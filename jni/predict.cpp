#include <jni.h>
#include <string.h>
#include <stdio.h>
#include <math.h>
#include "log.h"
#include "classifier.h"
#include "./svm/svm-predict.h"

#define LOG_TAG "PREDICT"

int predict(const char *modelFile, const char *dataFile, const char*outFile) {
    int cmdLen = 4;
    char *cmd[cmdLen];

    cmd[0] = "donotcare";

    int len = strlen(dataFile);
    cmd[1] = (char *)calloc(len+1, sizeof(char));
    strncpy(cmd[1], dataFile, len);
    cmd[1][len] = '\0';

    len = strlen(modelFile);
    cmd[2] = (char *)calloc(len+1, sizeof(char));
    strncpy(cmd[2], modelFile, len);
    cmd[2][len] = '\0';

    len = strlen(outFile);
    cmd[3] = (char *)calloc(len+1, sizeof(char));
    strncpy(cmd[3], outFile, len);
    cmd[3][len] = '\0';

    return svmpredict(cmdLen, cmd);
}
