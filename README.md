# Libsvm-androidjni -- LIBSVM for android jni environment

    SVM is a machine learning and classification algorithm, and LIBSVM is
    a popular free implementation of it, written by Chih-Chung Chang and
    Chih-Jen Lin, of National Taiwan University, Taipei.

    Libsvm-androidjni is a modification of the original LIBSVM that runs in
    Android jni framework to significantly reduce computation time. The
    functionality and interface of LIBSVM remains the same. The primary change
    including:
    
    1. Prediction interface is changed to be similar to Matlab version. Please
       refer to "Usage" for detailed change.
    2. Accuracy calculation is removed and should be done in Java code.
    3. Native log function is Supported.

    There is no Java code available since it's pretty straight forward to
    call these functions in JNI as long as you know how to write native code
    for Java. A simple Eclipse Android project example is available upon
    request, though.

## Versions

    Currently using libsvm version 3.11

## Progress

    1. Svm-scale is not ported.
    2. Probability prediction is not supported yet.

## Usage

    Functionality and User interface for training part is similar to Linux
    version of LIBSVM. While for prediction part, they are similar to Matlab
    version.

    The train.cpp and predict.cpp contained in svm folder are two example
    files about how to use the ported svm library.

    Data input for both training and prediction should include array(vector)
    of data(numbers): value1, value2, .... valueN (and the order of the values
    are specified by the respective index), and the class (or the result) of
    this array is label.

    The reason why there are multiple values for each vector is that the input
    data to the problem you were trying to solve involves lots of 'features', or
    say 'attributes', so the input will be a set (or say vector/array). For example,
    Any 2-dimension point has coordinates X and Y so it has two attributes (X and Y).
    To describe two points (0,3) and (5,8) as having labels(classes) 1 and 2,
    we will write them as:

        label1 1:0 2:3
        label2 1:5 2:8

    This kind of data structure has the advantage that we can specify a sparse
    matrix, ie. some attribute of a record can be omitted.


    ************Training***********

    Just like how you use the LIBSVM binaries, you need to specify a training
    data set file and a model file. Information of trained model will be written
    into that file.

    Please refer to the links in Notes for detailed information about training file
    format.

    Only several critical parameters are supported for training. Due to limited time,
    there is no plan to address this issue and make it universal. Other parameters
    can be supported by applying slight change to the interface in train.cpp file


    ************Prediction***********

    Following data structures are demanded:

    A array of data vector: value1, value2, ... , valueN
    A array of index vector: index1, index2, ..., indexN
    Dimensions of the matrix: rowNum, colNum
    Flag for probability prediction: always false(0) for now
    A model file: obtained from training
    A array of labels: Predicted labels for each data vector

    Data vector array and index vector array should be two matrices with
    exactly same dimensions. Predicted result will be written into labels
    array, therefore, make sure the array you passed in has enough space.


### Notes

[http://www.csie.ntu.edu.tw/~cjlin/libsvm/](http://www.csie.ntu.edu.tw/~cjlin/libsvm/)

[http://ntu.csie.org/~piaip/svm/svm_tutorial.html#](LIBSVM tutorial)
