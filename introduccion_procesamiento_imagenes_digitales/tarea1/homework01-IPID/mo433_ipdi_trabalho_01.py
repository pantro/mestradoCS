# -*- coding: utf-8 -*-
"""MO433-IPDI-TRABALHO_01.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1gHzvD4OxeVNq7PhIsFT3vkWfswNFPijS

# UNIVERSIDADE ESTADUAL DE CAMPINAS INSTITUTE OF COMPUTING

## INTRODUCTION TO DIGITAL IMAGE PROCESSING
MC920 / MO443

### HOMEWORK 1

STUDENT: Gian Franco Joel Condori Luna
RA: 234826
"""

#import necessary libraries
from google.colab import drive
drive.mount('/content/drive')
import cv2
import numpy as np
import matplotlib.pyplot as plt

#To know what files we have in this route
!ls "/content/drive/My Drive/imagenes/imagens_coloridas"

"""# Problem 1
*   R’= 0.393R+ 0.769G+ 0.189B
*   G’= 0.349R+ 0.686G+ 0.168B
*   B’= 0.272R+ 0.534G+ 0.131B
"""

#Read the colorful picture given by the teacher
img_colorida = cv2.imread("/content/drive/My Drive/imagenes/imagens_coloridas/baboon.png") # En cv2 los colores se guardan BGR
#We change the order from BGR to RGB so that we do not have an inverted image
b,g,r = cv2.split(img_colorida)
rgb_img = cv2.merge([r,g,b]) 
plt.imshow(rgb_img)

color = ('b','g','r')

for i, c in enumerate(color):
    hist = cv2.calcHist([rgb_img], [i], None, [256], [0, 256])
    plt.plot(hist, color = c)
    plt.xlim([0,256])

plt.show()

#We create a function that will be in charge of checking that each pixel and we limit it to 255
def limit255( px ):
  n_px = np.where(px<0, 0, np.where(px>255, px%256, px))
  return n_px

#To perform mathematical transformations of images, normally we need the image to be of type float32, for which we carry out the conversion using the numpy library
rgb_img = np.asarray(rgb_img,dtype=np.float32) 

#R’=  0.393R+ 0.769G+ 0.189B
Rp = limit255(0.393*rgb_img[:,:,0] + 0.769*rgb_img[:,:,1]+ 0.189*rgb_img[:,:,2])
#G’=  0.349R+ 0.686G+ 0.168B
Gp = limit255(0.349*rgb_img[:,:,0] + 0.686*rgb_img[:,:,1]+ 0.168*rgb_img[:,:,2])
#B' = 0.272R+ 0.534G+ 0.131B
Bp = limit255(0.272*rgb_img[:,:,0] + 0.534*rgb_img[:,:,1]+ 0.131*rgb_img[:,:,2])
#Together
new_img = cv2.merge([Rp,Gp,Bp])
#Convert format new uint8
new_img = new_img.astype(np.uint8)
plt.imshow(new_img)

color = ('b','g','r')

for i, c in enumerate(color):
    hist = cv2.calcHist([new_img], [i], None, [256], [0, 256])
    plt.plot(hist, color = c)
    plt.xlim([0,256])

plt.show()

"""# Problem 2

*   I= 0.2989R+ 0.5870G+ 0.1140B




"""

#I= 0.2989R+ 0.5870G+ 0.1140B
I = limit255(0.2989*rgb_img[:,:,0] + 0.5870*rgb_img[:,:,1]+ 0.1140*rgb_img[:,:,2])
#Convert a uint8
I = I.astype(np.uint8)
plt.imshow(I)

hist = cv2.calcHist([I], [0], None, [256], [0, 256])
plt.plot(hist, color = c)
plt.xlim([0,256])
plt.show()

"""1.2   Monochrome Images"""

#Load imagen
img_monocromatica = cv2.imread("/content/drive/My Drive/imagenes/imagens_png/baboon.png", cv2.IMREAD_GRAYSCALE)
plt.imshow(img_monocromatica, cmap = 'gray')
#Convirt a float
f_img_monocromatica = np.asarray(img_monocromatica,dtype=np.float32)

"""# Problema 3
## Filter H1:

> \begin{equation}
h_{1}=\begin{bmatrix}
1 & 0 & 1\\
-2 & 0 & 2\\
1 & 0 & 1
\end{bmatrix}
\end{equation}
"""

#Filter
h1 = np.array([[ 1, 0, 1],
               [-2, 0, 2],
               [ 1, 0, 1]])

#VECTORIAL
def conv_vectorial(image, kernel):
  height, width = image.shape        # Get image dimensions
  h, w = kernel.shape                 # Convolution kernel dimensions

  # Size of the new image obtained after the convolution operation
  new_h = height - h + 1
  new_w = width - w + 1
  
  iter_h = int(h /2)
  iter_w = int(w / 2)

  # Initialize the new image matrix
  new_image = np.zeros((height, width), dtype=np.float)
  new_image[:,:] = image[:,:]   

  # Perform convolution operation, multiply the values ​​of corresponding elements of the array
  for i in range(new_w*new_h):
    r = int(np.floor(i/new_w))
    c = int(i%new_w)  
    #The elements of the matrix are multiplied and accumulated
    new_image[r+iter_h, c+iter_w] = np.sum(image[r:r+h, c:c+w] * kernel)
  
  return new_image

result_h1 = conv_vectorial(f_img_monocromatica, h1)
#Convert a uint8
result_h1 = result_h1.astype('uint8')   
plt.imshow(result_h1, cmap = 'gray')

"""# Problem 4
## Filter H2:

> \begin{equation}
h_{2}=\begin{bmatrix}
-1 & -2 & -1\\
0 & 0 & 0\\
1 & 2 & 1
\end{bmatrix}
\end{equation}
"""

#Filter h2
h2 = np.array([[ -1, -2, -1],
               [  0, 0, 0],
               [  1, 2, 1]])
#Convolution
result_h2 = conv_vectorial(f_img_monocromatica, h2)
#Convert a uint8
result_h2 = result_h2.astype('uint8')
plt.imshow(result_h2, cmap = 'gray')

"""# Problem 5
## Filter H3:

> \begin{equation}
h_{3}=\begin{bmatrix}
-1 & -1 & -1\\
-1 & 8 & -1\\
-1 & -1 & -1
\end{bmatrix}
\end{equation}
"""

#Filter h3
h3 = np.array([[ -1, -1, -1],
               [ -1,  8, -1],
               [ -1, -1, -1]])
#Convolution
result_h3 = conv_vectorial(f_img_monocromatica, h3)
#Convert a uint8
result_h3 = result_h3.astype('uint8')   
plt.imshow(result_h3, cmap = 'gray')

"""# Problem 6
## Filter H4:

> \begin{equation}
h_{4}=1/9\begin{bmatrix}
1 & 1 & 1\\
1 & 1 & 1\\
1 & 1 & 1
\end{bmatrix}
\end{equation}
"""

#Filter h4
h4 = np.array([[ 1, 1, 1],
               [ 1, 1, 1],
               [ 1, 1, 1]])
#Convolution
result_h4 = conv_vectorial(f_img_monocromatica, h4)/9
#Convert a uint8
result_h4 = result_h4.astype('uint8')   
plt.imshow(result_h4, cmap = 'gray')

"""# Problem 7
## Filter H5:

> \begin{equation}
h_{5}=\begin{bmatrix}
-1 & -1 & 2\\
-1 & 2 & -1\\
2 & -1 & -1
\end{bmatrix}
\end{equation}
"""

#Filter h5
h5 = np.array([[ -1, -1,  2],
               [ -1,  2, -1],
               [  2, -1, -1]])
#Convolution
result_h5 = conv_vectorial(f_img_monocromatica, h5)
#Convert a uint8
result_h5 = result_h5.astype('uint8')   
plt.imshow(result_h5, cmap = 'gray')

"""#Problem 8
## Filter H6:

> \begin{equation}
h_{6}=\begin{bmatrix}
2 & -1 & -1\\
-1 & 2 & -1\\
-1 & -1 & 2
\end{bmatrix}
\end{equation}
"""

#Filter h6
h6 = np.array([[  2, -1, -1],
               [ -1,  2, -1],
               [ -1, -1,  2]])
#Convolution
result_h6 = conv_vectorial(f_img_monocromatica, h6)
#Convert a uint8
result_h6 = result_h6.astype('uint8')   
plt.imshow(result_h6, cmap = 'gray')

"""#Problem 9
## Filter H7:

> \begin{equation}
h_{7}=\begin{bmatrix}
0 & 0 & 1\\
0 & 0 & 0\\
-1 & 0 & 0
\end{bmatrix}
\end{equation}
"""

#Filter h7
h7 = np.array([[  0, 0, 1],
               [  0, 0, 0],
               [ -1, 0, 0]])
#Convolution
result_h7 = conv_vectorial(f_img_monocromatica, h7)
#Convert a uint8
result_h7 = result_h7.astype('uint8')   
plt.imshow(result_h7, cmap = 'gray')

"""#Problem 10
## Filter H8:

> \begin{equation}
h_{8}=\begin{bmatrix}
 0 &  0 & -1 &  0 & 0\\
 0 & -1 & -2 &  -1 & 0\\
-1 & -2 & 16 & -2 &-1\\
 0 & -1 & -2 & -1 & 0\\
 0 &  0 & -1 &  0 & 0
\end{bmatrix}
\end{equation}
"""

#Filter h8
h8 = np.array([[  0, 0, -1,  0,  0],
               [  0,-1, -2, -1,  0],
               [ -1,-2, 16, -2, -1],
               [  0,-1, -2, -1,  0],
               [  0, 0, -1,  0,  0]])
#Convolution
result_h8 = MyFilter(f_img_monocromatica, h8)
#Convert a uint8
result_h8 = result_h8.astype('uint8')
plt.imshow(result_h8, cmap = 'gray')

"""#Problem 11
## Filter H9:

> \begin{equation}
h_{9}=1/256\begin{bmatrix}
 1 &  4 &  6 &  4 & 1\\
 4 & 16 & 24 & 16 & 4\\
 6 & 24 & 36 & 24 & 6\\
 4 & 16 & 24 & 16 & 4\\
 1 &  4 &  6 &  4 & 1
\end{bmatrix}
\end{equation}
"""

#Filter h9
h9 = np.array([[ 1,  4, 6,  4,  1],
               [ 4, 16, 24, 16, 4],
               [ 6, 24, 36, 24, 6],
               [ 4, 16, 24, 16, 4],
               [ 1,  4,  6,  4, 1]])
#Convolution
result_h9 = MyFilter(f_img_monocromatica, h9)/256
#Convert a uint8
result_h9 = result_h9.astype('uint8')   
plt.imshow(result_h9, cmap = 'gray')

"""##Problem 12
#Combined filter h1 and h2 in operation:
> \begin{equation}
\sqrt[2]{(h1)^{2}+{(h2)^{2}}}
\end{equation}
"""

f_result_h1 = conv_vectorial(f_img_monocromatica, h1)
f_result_h2 = conv_vectorial(f_img_monocromatica, h2)
result_h1_h2 = np.sqrt((f_result_h1*f_result_h1)+(f_result_h2*f_result_h2))
#Convert a uint8
result_h1_h2 = result_h1_h2.astype('uint8')   
plt.imshow(result_h1_h2, cmap = 'gray')