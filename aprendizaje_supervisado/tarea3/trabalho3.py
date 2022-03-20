# -*- coding: utf-8 -*-
"""
Created on Sat Jun 19 18:10:53 2021

@author: W10
"""

import numpy as np
from numpy import linalg 
import matplotlib.pyplot as plt
import pandas as pd
import cv2
import io

import math
from keras.models import Sequential
from keras.layers import Dense
from keras.layers import LSTM
from sklearn.preprocessing import MinMaxScaler
from sklearn.metrics import mean_squared_error
from sklearn.metrics import r2_score #Accuracy pra valores continuos

dataFile = "ouro2.csv"
df = pd.read_csv(dataFile, sep=',') 
df = df[::-1]
date = df['Data'].to_numpy()
df = df.drop(['Data'], axis=1) #Eliminar a coluna Data
pd.concat([df.head(), df.tail()])

data = df.to_numpy()
scaler = MinMaxScaler(feature_range=(0, 1))
data_norm = scaler.fit_transform(data)

Xtest = data_norm[len(data_norm)-100:]
Xtrain = data_norm[:len(data_norm)-100]

plt.plot(data_norm)
plt.show()

# fix random seed for reproducibility
np.random.seed(7)

# convert an array of values into a dataset matrix
def create_dataset(dataset, look_back=1):
	dataX, dataY = [], []
	for i in range(len(dataset)-look_back-1):
		a = dataset[i:(i+look_back), 0]
		dataX.append(a)
		dataY.append(dataset[i + look_back, 0])
	return np.array(dataX), np.array(dataY)

XtestCV =  Xtrain[len(Xtrain)-100:]
XtrainCV = Xtrain[:len(Xtrain)-100]

#Hiperparameters
lb = [4, 5, 10, 15, 20, 25, 30]
neurons = [5, 7, 10, 12]
epochs_ = [50, 75, 100]

bestlb = 20
bestNeurons = 5
bestEpochs = 75
RMSE = 9999

"""
for lb_ in lb:
  for nr_ in neurons:
    for ep in epochs_:
      look_back = lb_
      trainX, trainY = create_dataset(XtrainCV, look_back)
      testX, testY = create_dataset(XtestCV, look_back)

      trainX = np.reshape(trainX, (trainX.shape[0], 1, trainX.shape[1]))
      testX = np.reshape(testX, (testX.shape[0], 1, testX.shape[1]))

      model = Sequential()
      model.add(LSTM(nr_, input_shape=(1, look_back)))
      model.add(Dense(1))
      model.compile(loss='mean_squared_error', optimizer='adam')
      model.fit(trainX, trainY, epochs=ep, batch_size=1, verbose=0)

      trainPredict = model.predict(trainX)
      testPredict = model.predict(testX)

      # invert predictions
      testPredict_ = scaler.inverse_transform(testPredict)
      testY_ = scaler.inverse_transform([testY])
      
      testScore = math.sqrt(mean_squared_error(testY_[0], testPredict_[:,0]))

      if RMSE > testScore:
        print("Best score: ", testScore)
        RMSE = testScore
        bestlb = lb_
        bestNeurons = nr_
        bestEpochs = ep
"""

look_back = bestlb
trainX, trainY = create_dataset(Xtrain, look_back)
testX, testY = create_dataset(Xtest, look_back)

trainX = np.reshape(trainX, (trainX.shape[0], 1, trainX.shape[1]))
testX = np.reshape(testX, (testX.shape[0], 1, testX.shape[1]))

model = Sequential()
model.add(LSTM(bestNeurons, input_shape=(1, look_back)))
model.add(Dense(1))
model.compile(loss='mean_squared_error', optimizer='adam')
model.fit(trainX, trainY, epochs=bestEpochs, batch_size=1, verbose=0)

trainPredict = model.predict(trainX)
testPredict = model.predict(testX)


# invert predictions
trainPredict_ = scaler.inverse_transform(trainPredict)
trainY_ = scaler.inverse_transform([trainY])
testPredict_ = scaler.inverse_transform(testPredict)
testY_ = scaler.inverse_transform([testY])

# calculate root mean squared error
trainScore = math.sqrt(mean_squared_error(trainY_[0], trainPredict_[:,0]))
print('Train Score: %.2f RMSE' % (trainScore))
trainAccuracy = r2_score(trainY_[0], trainPredict_[:,0])
print('Test Score: %.2f ACCURACY' % (trainAccuracy))

testScore = math.sqrt(mean_squared_error(testY_[0], testPredict_[:,0]))
print('Test Score: %.2f RMSE' % (testScore))
testAccuracy = r2_score(testY_[0], testPredict_[:,0])
print('Test Score: %.2f ACCURACY' % (testAccuracy))

date1 = date[len(date)-len(testY_[0]):]
len(date1)
date1 = date1[0::10]
len(date1)

plt.plot(testY_[0], color='b', label='True')
plt.plot(testPredict_[:,0], color='r', label='Prediction')
plt.xticks([0, 10, 20, 30, 40, 50, 60, 70], date1, rotation='vertical')
plt.legend()