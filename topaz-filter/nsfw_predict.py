#-*-coding:utf-8-*-

import os
import sys
import shutil

import json
import pika
import numpy as np
from PIL import Image
import tensorflow as tf


_MODEL_DIR = os.path.join(os.path.abspath(os.path.dirname(__file__)), 'data/models/1547856517')

_IMAGE_SIZE = 64
_BATCH_SIZE = 128

_LABEL_MAP = {0:'drawings', 1:'hentai', 2:'neutral', 3:'porn', 4:'sexy'}

config={
        "prefix": "/topaz/unchecked/",
        "passed_prefix": "/topaz/image/"
}

def standardize(img):
    mean = np.mean(img)
    std = np.std(img)
    img = (img - mean) / std
    return img

def load_image( infilename ) :
    img = Image.open( infilename )
    img = img.resize((_IMAGE_SIZE, _IMAGE_SIZE))
    img.load()
    data = np.asarray( img, dtype=np.float32 )
    if data.shape[2] == 4:
        img = Image.open(infilename).convert("RGB")
        img = img.resize((_IMAGE_SIZE, _IMAGE_SIZE))
        img.load()
        data = np.asarray( img, dtype=np.float32 )
    data = standardize(data)
    return data

def predict(image_path):
    with tf.Session() as sess:
        # sess = tf.Session(config=tf.ConfigProto(log_device_placement=True))
        graph = tf.get_default_graph();
        tf.saved_model.loader.load(sess, [tf.saved_model.tag_constants.SERVING], _MODEL_DIR)
        inputs = graph.get_tensor_by_name("input_tensor:0")
        probabilities_op = graph.get_tensor_by_name('softmax_tensor:0')
        class_index_op = graph.get_tensor_by_name('ArgMax:0')

        image_data = load_image(image_path)
        probabilities, class_index = sess.run([probabilities_op, class_index_op],
                                              feed_dict={inputs: [image_data] * _BATCH_SIZE})

        probabilities_dict = {_LABEL_MAP.get(i): l for i, l in enumerate(probabilities[0])}
        pre_label = _LABEL_MAP.get(class_index[0])
        result = {"class": pre_label, "probability": probabilities_dict}
        return result


def callback(ch, method, properties, body):
    body=body.decode()
    print("come", body)
    try:
        res=predict(config["prefix"]+body)
    except:
        print("Error")
    else:
        print(res)
        if res['class'] in ['hentai', 'porn', 'sexy']:
            print(body, "not passed")
        else:
            shutil.copyfile(config["prefix"]+body, config['passed_prefix']+body)

connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
channel = connection.channel()
channel.basic_consume(
    queue='topaz.queue'+sys.argv[1], on_message_callback=callback, auto_ack=True)

if not os.path.exists(config['prefix']):
    os.makedirs(config['prefix'])

if not os.path.exists(config['passed_prefix']):
    os.makedirs(config['passed_prefix'])


print("image filter is listening...")
channel.start_consuming()
