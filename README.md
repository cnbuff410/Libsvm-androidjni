# Libsvm-androidjni -- LIBSVM for android jni environment

    SVM is a machine learning and classification algorithm, and LIBSVM is
    a popular free implementation of it, written by Chih-Chung Chang and
    Chih-Jen Lin, of National Taiwan University, Taipei.

    Libsvm-androidjni is a modification of the original LIBSVM that runs in
    Android jni framework to significantly reduce computation time. The
    functionality and interface of LIBSVM remains the same. The primary change
    is that previous main function entry is now renamed and can be called by
    other code.

## Versions

    Currently using libsvm version 3.11

## Usage
    Functionality and User interface are exactly same as LIBSVM. Native log function is Supported.

    The train.cpp and predict.cpp are two example files about how to use the
    svm library, which is under svm folder, in native environment.

### Notes

        [http://www.csie.ntu.edu.tw/~cjlin/libsvm/](http://www.csie.ntu.edu.tw/~cjlin/libsvm/)

        [http://ntu.csie.org/~piaip/svm/svm_tutorial.html#](LIBSVM tutorial)
