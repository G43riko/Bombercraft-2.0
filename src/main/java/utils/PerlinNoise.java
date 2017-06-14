package utils;

public class PerlinNoise {
	public static float[][] GeneratePerlinNoise(float[][] baseNoise, int octaveCount,float persistance, boolean normalize){
		int width = baseNoise.length;
		int height = baseNoise[0].length;
		
		float[][][] smoothNoise = new float[octaveCount][][]; //an array of 2D arrays containing
		
		//generate smooth noise
		for (int i=0 ; i<octaveCount ; i++)
			smoothNoise[i] = GenerateSmoothNoise(baseNoise, i);
		
		
		float[][] perlinNoise = new float[width][height];
	    float amplitude = 1.0f;
	    float totalAmplitude = 0.0f;
	    
	    //blend noise together
	    for (int octave=octaveCount-1 ; octave>=0 ; octave--){
	       amplitude *= persistance;
	       totalAmplitude += amplitude;
	       
	       for (int i = 0; i < width; i++)
	          for (int j = 0; j < height; j++)
	             perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
	    }
	    
	    //normalisation
	    if(normalize)
	    	for (int i = 0; i< width; i++)
	    		for (int j = 0; j < height; j++)
	    			perlinNoise[i][j] /= totalAmplitude;
	    		
	    else
	    	for (int i = 0; i< width; i++)
	    		for (int j = 0; j < height; j++)
	    			perlinNoise[i][j] = Math.min(255,Math.max(0, perlinNoise[i][j]));

	    return perlinNoise;
	}
	
	public static float[][] generateWhiteNoise(int x, int y){
		float[][] result = new float[x][y];
		for(int i=0 ; i<x ; i++)
			for(int j=0 ; j<y ; j++)
				result[i][j] = (float)Math.random();
			
		return result;
	}
	
	public static float[][][] generateWhiteNoise(int x, int y,int z){
		float[][][] result = new float[x][y][z];
		for(int i=0 ; i<x ; i++)
			for(int j=0 ; j<y ; j++)
				for(int k=0 ; k<z ; k++)
					result[i][j][z] = (float)Math.random();

		return result;
	}
	
	public static float Interpolate(float x0, float x1, float alpha){
		return x0 * (1 - alpha) + alpha * x1;
	}
	
	public static float[][] GenerateSmoothNoise(float[][] baseNoise, int octave){
		int width = baseNoise.length;
		int height = baseNoise[0].length;
		
		float [][] smoothNoise = new float[width][height];
		
		int samplePeriod = 1 << octave; // calculates 2 ^ k
		float sampleFrequency = 1.0f / samplePeriod;
		
		for(int i=0 ; i<width ; i++){
			//calculate the horizontal sampling indices
			int sample_i0 = (i / samplePeriod) * samplePeriod;
			int sample_i1 = (sample_i0 + samplePeriod) % width; //wrap around
			float horizontal_blend = (i - sample_i0) * sampleFrequency;
			
			for(int j=0 ; j<height ; j++){
				//calculate the vertical sampling indices
				int sample_j0 = (j / samplePeriod) * samplePeriod;
				int sample_j1 = (sample_j0 + samplePeriod) % height; //wrap around
				float vertical_blend = (j - sample_j0) * sampleFrequency;
				
				//blend the top two corners
				float top = Interpolate(baseNoise[sample_i0][sample_j0],
				baseNoise[sample_i1][sample_j0], horizontal_blend);
				
				//blend the bottom two corners
				float bottom = Interpolate(baseNoise[sample_i0][sample_j1],
				baseNoise[sample_i1][sample_j1], horizontal_blend);
				
				//final blend
				smoothNoise[i][j] = Interpolate(top, bottom, vertical_blend);
			}
		}
		
		return smoothNoise;
	}
}
