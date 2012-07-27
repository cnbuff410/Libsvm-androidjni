package info.kunli.androidlibsvmexample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AndroidLibsvmExampleActivity extends Activity {
    /** Called when the activity is first created. */
	private static final String TAG = "Libsvm";
	
    // svm native
    private native int trainClassifierNative(String trainingFile, int kernelType,
    		int cost, float gamma, int isProb, String modelFile);
    private native int doClassificationNative(float values[][], int indices[][],
    		int isProb, String modelFile, int labels[], double probs[]);
    
 // Load the native library
    static {
        try {
            System.loadLibrary("signal");
        } catch (UnsatisfiedLinkError ule) {
            Log.e(TAG, "Hey, could not load native library signal");
        }
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button trainButton = (Button)findViewById(R.id.train);
        Button classifyButton = (Button)findViewById(R.id.classifiy);
        
        trainButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				train();				
			}
		});
        classifyButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				classify();				
			}
		});
    }
    
    private void train() {
    	// Svm training
    	int kernelType = 2; // Radial basis function
    	int cost = 4; // Cost
    	int isProb = 0;
    	float gamma = 0.25f; // Gamma
    	String trainingFileLoc = Environment.getExternalStorageDirectory()+"/training_set";
    	String modelFileLoc = Environment.getExternalStorageDirectory()+"/model";
    	if (trainClassifierNative(trainingFileLoc, kernelType, cost, gamma, isProb,
    			modelFileLoc) == -1) {
    		Log.d(TAG, "training err");
    		finish();
    	}
    	Toast.makeText(this, "Training is done", 2000).show();
    }
    
    /**
     * classify generate labels for features.
     * Return:
     * 	-1: Error
     * 	0: Correct
     */
    public int callSVM(float values[][], int indices[][], int groundTruth[], int isProb, String modelFile,
    		int labels[], double probs[]) {
    	// SVM type
    	final int C_SVC = 0;
    	final int NU_SVC = 1;
    	final int ONE_CLASS_SVM = 2;
    	final int EPSILON_SVR = 3;
    	final int NU_SVR = 4;
    	
    	// For accuracy calculation
    	int correct = 0;
    	int total = 0;
    	float error = 0;
    	float sump = 0, sumt = 0, sumpp = 0, sumtt = 0, sumpt = 0;
    	float MSE, SCC, accuracy;  	

    	int num = values.length;
    	int svm_type = C_SVC;
    	if (num != indices.length)
    		return -1;
    	// If isProb is true, you need to pass in a real double array for probability array
        int r = doClassificationNative(values, indices, isProb, modelFile, labels, probs);
        
        // Calculate accuracy
        if (groundTruth != null) {
        	if (groundTruth.length != indices.length) {
        		return -1;
        	}
        	for (int i = 0; i < num; i++) {
            	int predict_label = labels[i];
            	int target_label = groundTruth[i];
            	if(predict_label == target_label)
            		++correct;
    	        error += (predict_label-target_label)*(predict_label-target_label);
    	        sump += predict_label;
    	        sumt += target_label;
    	        sumpp += predict_label*predict_label;
    	        sumtt += target_label*target_label;
    	        sumpt += predict_label*target_label;
    	        ++total;
            }
            
        	if (svm_type==NU_SVR || svm_type==EPSILON_SVR)
        	{
        		MSE = error/total; // Mean square error
        		SCC = ((total*sumpt-sump*sumt)*(total*sumpt-sump*sumt)) / ((total*sumpp-sump*sump)*(total*sumtt-sumt*sumt)); // Squared correlation coefficient
        	}
        	accuracy = (float)correct/total*100;
            Log.d(TAG, "Classification accuracy is " + accuracy);
        }       
        
        return r;
    }
    
    private void classify() {
        // Svm classification
        float[][] values = {
                        {0.708333f, 1, 1, -0.320755f, -0.105023f, -1, 1, -0.419847f, -1, -0.225806f, 1, -1 },
                        {0.583333f, -1, 0.333333f, -0.603774f, 1, -1, 1, 0.358779f, -1, -0.483871f, -1, 1},
                        {0.166667f, 1, -0.333333f, -0.433962f, -0.383562f, -1, -1, 0.0687023f, -1, -0.903226f, -1, 1},
                        {0.458333f, 1, 1, -0.358491f, -0.374429f, -1, 1, -0.480916f, 1, -0.935484f, -0.333333f, 1 },
        };
        int[][] indices = {
                        {1,2,3,4,5,6,7,8,9,10,12,13},
                        {1,2,3,4,5,6,7,8,9,10,12,13},
                        {1,2,3,4,5,6,7,8,9,10,12,13},
                        {1,2,3,4,5,6,7,8,9,10,12,13}
        };
        int[] groundTruth = null;
        int[] labels = new int[4];
        double[] probs = new double[4];
        int isProb = 0; // Not probability prediction
        String modelFileLoc = Environment.getExternalStorageDirectory()+"/model";

        if (callSVM(values, indices, groundTruth, isProb, modelFileLoc, labels, probs) != 0) {
                Log.d(TAG, "Classification is incorrect");
        }
        else {
        	String m = "";
        	for (int l : labels)
        		m += l + ", ";
        	Toast.makeText(this, "Classification is done, the result is " + m, 2000).show();
        }
    }
}