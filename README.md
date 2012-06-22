# Libsvm-androidjni -- LIBSVM for android jni environment

    SVM is a machine learning and classification algorithm, LIBSVM is a popular free implementation of it, 
    written by Chih-Chung Chang and Chih-Jen Lin, of National Taiwan University, Taipei.

    Libsvm-androidjni is a modification version of the original LIBSVM that runs in Android JNI framework to 
    significantly reduce computation time. The functionality and interface of LIBSVM remains the same. 
    Primary changes including:
    
    * Prediction interface is changed to be similar to Matlab version. Please refer to "Usage" for detailed change.
    * Accuracy calculation is removed and should be done in Java code. The usage is included in the example project.
    * Log function is Supported under native environment.

    A simple Android project example is available. Again, refer to "Usage" for specific instruction on how to 
    compile. Shoot me a email at <likunarmstrong@gmail.com> if you have any other question.

- - - 
## Versions

    Currently using libsvm version 3.11.

- - - 
## Usage

    Functionality and User interface for training part is similar to Linux version of LIBSVM. While for 
    prediction part, they are similar to Matlab version.

    Data input for both training and prediction should include array(vector) of data(numbers): 
    value1, value2, .... valueN (and the order of the values are specified by the respective index), 
    and the class (or the result) of this array is label.

    The reason why there are multiple values for each vector is that the input data to the problem you were 
    trying to solve involves lots of 'features', or say 'attributes', so the input will be a set/array. 
    For example, Any 2-dimension point has coordinates X and Y so it has two attributes (X and Y).
    
    To describe two points (0,3) and (5,8) as having labels(classes) 1 and 2, we will write them as:

        label1 1:0 2:3
        label2 1:5 2:8

    This kind of data structure has the advantage that we can specify a sparse matrix, ie. some attribute 
    of a record can be omitted.

    ************Android project***********

    To compile and run this project, follow the steps below:

    1. Copy "training_set" file to sdcard. Change the variable "trainingFileLoc" in Java code to be the its location.
    2. Run "ndk_compile" script to compile all native code into native library for Java code to call.
    3. Use Eclipse to compile and install the whole app.

    There are two buttons on the UI, one is for training and one is for testing. The app will exit if any of 
    them returns incorrect result, otherwise the training or testing will be running correctly and you will either:

    1. Find the trained model file in the same folder as training set file, or
    2. See the classification result in Toast message.

    ************Training***********

    Just like how you use the LIBSVM binaries, you need to specify a training data set file and a model file. 
    Information of trained model will be written into that file.

    The "training_set" file demonstrates the standard format for training file. Please refer to the second links 
    in "Notes" for detailed information.

    Only several critical parameters are supported for training. Due to limited time, there is no plan to address 
    this issue and make it universal. Other parameters can be supported by applying slight change to the interface 
    in train.cpp file


    ************Prediction***********

    Following data structures are demanded:

    A array of data vector: value1, value2, ... , valueN
    A array of index vector: index1, index2, ..., indexN
    Dimensions of the matrix: rowNum, colNum
    Flag for probability prediction: 0 for no, 1 for yes
    A model file: obtained from training
    A array of labels: Predicted labels for each data vector

    Data vector array and index vector array should be two matrices with exactly same dimensions. 
    Predicted result will be written into labels array, therefore, make sure the array you passed in has enough space.

- - - 
## TODO

    1. Svm-scale is not ported.

- - -
## Update

    Probablity prediction is supported, thank kuoyenlo for his patch.

### Notes

[http://www.csie.ntu.edu.tw/~cjlin/libsvm/](http://www.csie.ntu.edu.tw/~cjlin/libsvm/)

[http://ntu.csie.org/~piaip/svm/svm_tutorial.html#](LIBSVM tutorial)
