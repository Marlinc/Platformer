package perlin;

import java.util.Random;

public class PerlinNoise {
    public int seed;
    public Random random;
    public double persistence = 0.25f;
    public int octaves = 6;
    private double[] result1d;
    private double[][] result2d;
    public boolean cosinus = true;
    public double zoom = 1.0;
    private int startX, startY;
    
    public PerlinNoise()
    {
        seed   = new Random().nextInt();
        random = new Random(seed);
    }
    public PerlinNoise (int seed)
    {
        this.seed = seed;
        random = new Random(seed);
    }

    public double[] getNoiseMap1d(int length, int startX) {
        startX += 1073741824;
        result1d = new double[length];
        this.startX = startX;
        for (int i = 0; i < length; i++) {
            result1d[i] = getInterpolatedNoise1d(i + startX);
        }
        double map[] = result1d;
        result1d = null;
        return map;
    }

    public double getRandomnumber(int seed) {
        seed = this.seed ^ seed;
        seed = (seed >> 13) ^ seed;
        return (double) (1.0 - ((seed * (seed * seed * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824.0);
    }

    public double interpolateCos(double a, double b, double x) {
        if (!cosinus) {
            return a * (1 - x) + b * x;
        }
        double f = (double) ((1 - Math.cos(x * 3.1415926535f)) * 0.5f);
        return a * (1 - f) + b * f;
    }

    public double getNoise1d(int x) {
        return getRandomnumber((x << 13) ^ x);
    }

    public double getSmoothedNoise1d(int x) {
        return getNoise1d(x) / 2 + getNoise1d(x - 1) / 4 + getNoise1d(x + 1)
                / 4;
    }

    public double getInterpolatedNoise1d(double x) {
        if (result1d[(int) (x - startX)] != 0.0) return result1d[(int) (x)];
        x /= zoom;
        double fx = x % 1;
        int ix = (int) (x - fx);

        return interpolateCos(getSmoothedNoise1d(ix),
                getSmoothedNoise1d(ix + 1), fx);
    }

    public double getPerlinNoise1d(double x) {
        double total = 0;

        for (int i = 0; i < (octaves - 1); i++) {
            double frequency = (double) Math.pow(2, i);
            double amplitude = (double) Math.pow(persistence, i);
            total += getInterpolatedNoise1d(x * frequency) * amplitude;
        }
        return total;
    }

    public double getNoise2d(int x, int y) {
        int n = x + y * 57;
        return getRandomnumber((n << 13) ^ n);
    }

    public double getSmoothedNoise2d(int x, int y) {
        double corners = (getNoise2d(x - 1, y - 1) + getNoise2d(x + 1, y - 1)
                + getNoise2d(x - 1, y + 1) + getNoise2d(x + 1, y + 1)) / 16f;
        double sides = (getNoise2d(x - 1, y) + getNoise2d(x + 1, y)
                + getNoise2d(x, y - 1) + getNoise2d(x, y + 1)) / 8f;
        double center = getNoise2d(x, y) / 4f;
        return corners + sides + center;
    }

    public double getInterpolatedNoise2d(double x, double y) {
        double fx = x % 1;
        int ix = (int) (x - fx);
        double fy = y % 1;
        int iy = (int) (y - fy);

        double v1 = getSmoothedNoise2d(ix, iy);
        double v2 = getSmoothedNoise2d(ix + 1, iy);
        double v3 = getSmoothedNoise2d(ix, iy + 1);
        double v4 = getSmoothedNoise2d(ix + 1, iy + 1);

        return interpolateCos(interpolateCos(v1, v2, fx),
                interpolateCos(v3, v4, fx), fy);
    }

    public double getPerlinNoise2d(double x, double y) {
        //System.out.println((int) (x - startX));
        if (result2d[(int) (x - startX)][(int) (y - startY)] != 0.0) 
            return result2d[(int) (x - startX)][(int) (y - startY)];
        x /= zoom;
        y /= zoom;
        double total = 0;
        for (int i = 0; i < octaves; i++) {
            double frequency = (double) Math.pow(2, i);
            double amplitude = (double) Math.pow(persistence, i);

            total += getInterpolatedNoise2d(x * frequency, y * frequency)
                    * amplitude;
        }
        return total;
    }
    public double[][] getNoiseMap2d(int width, int height) {
        return getNoiseMap2d(0, 0, width, height);
    }
    public double[][] getNoiseMap2d(int startX, int startY, int width, int height) {
        //System.out.println((int) ((double) Math.pow(2, 32)));
        startX += 1073741824;
        startY += 1073741824;
        this.startX = startX;
        this.startY = startY;
        result2d = new double[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                result2d[x][y] = getPerlinNoise2d(
                        (startX + x), 
                        (startY + y));
            }
        }
        double[][] map = result2d;
        result2d = null;
        return map;
    }
}
/*
package perlin;

import java.util.Random;

public class PerlinNoise {
    public int seed;
    public Random random;
    public float persistence = 0.25f;
    public int octaves = 6;
    private float[] result1d;
    private float[][] result2d;
    public boolean cosinus = true;
    private int startX, startY;
    public PerlinNoise() {
        seed = new Random().nextInt();
        random = new Random(seed);
    }

    public float[] getNoiseMap1d(int length) {
        result1d = new float[length];

        for (int i = 0; i < length; i++) {
            result1d[i] = getInterpolatedNoise1d(i);
        }
        float map[] = result1d;
        result1d = null;
        return map;
    }

    public float getRandomnumber(int seed) {
        seed = this.seed ^ seed;
        seed = (seed >> 13) ^ seed;
        return (float) (1.0 - ((seed * (seed * seed * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824.0);
    }

    public float interpolateCos(float a, float b, float x) {
        if (!cosinus) {
            return a * (1 - x) + b * x;
        }
        float f = (float) ((1 - Math.cos(x * 3.1415926535f)) * 0.5f);
        return a * (1 - f) + b * f;
    }

    public float getNoise1d(int x) {
        return getRandomnumber((x << 13) ^ x);
    }

    public float getSmoothedNoise1d(int x) {
        return getNoise1d(x) / 2 + getNoise1d(x - 1) / 4 + getNoise1d(x + 1)
                / 4;
    }

    public float getInterpolatedNoise1d(float x) {
        if (result1d[(int) (x)] != 0.0) return result1d[(int) (x)]; 
        float fx = x % 1;
        int ix = (int) (x - fx);

        return interpolateCos(getSmoothedNoise1d(ix),
                getSmoothedNoise1d(ix + 1), fx);
    }

    public float getPerlinNoise1d(float x) {
        float total = 0;

        for (int i = 0; i < (octaves - 1); i++) {
            float frequency = (float) Math.pow(2, i);
            float amplitude = (float) Math.pow(persistence, i);
            total += getInterpolatedNoise1d(x * frequency) * amplitude;
        }
        return total;
    }

    public float getNoise2d(int x, int y) {
        int n = x + y * 57;
        return getRandomnumber((n << 13) ^ n);
    }

    public float getSmoothedNoise2d(int x, int y) {
        float corners = (getNoise2d(x - 1, y - 1) + getNoise2d(x + 1, y - 1)
                + getNoise2d(x - 1, y + 1) + getNoise2d(x + 1, y + 1)) / 16f;
        float sides = (getNoise2d(x - 1, y) + getNoise2d(x + 1, y)
                + getNoise2d(x, y - 1) + getNoise2d(x, y + 1)) / 8f;
        float center = getNoise2d(x, y) / 4f;
        return corners + sides + center;
    }

    public float getInterpolatedNoise2d(float x, float y) {
        float fx = x % 1;
        int ix = (int) (x - fx);
        float fy = y % 1;
        int iy = (int) (y - fy);

        float v1 = getSmoothedNoise2d(ix, iy);
        float v2 = getSmoothedNoise2d(ix + 1, iy);
        float v3 = getSmoothedNoise2d(ix, iy + 1);
        float v4 = getSmoothedNoise2d(ix + 1, iy + 1);

        return interpolateCos(interpolateCos(v1, v2, fx),
                interpolateCos(v3, v4, fx), fy);
    }

    public float getPerlinNoise2d(float x, float y) {
        int ix = (int) x;
        int iy = (int) y;
        System.out.println((int) (x - startX));
        if (result2d[(int) (x - startX)][(int) (y - startY)] != 0.0) 
            return result2d[(int) (x - startX)][(int) (y - startY)];
        float total = 0;
        for (int i = 0; i < octaves; i++) {
            float frequency = (float) Math.pow(2, i);
            float amplitude = (float) Math.pow(persistence, i);

            total += getInterpolatedNoise2d(x * frequency, y * frequency)
                    * amplitude;
        }
        return total;
    }
    public float[][] getNoiseMap2d(int width, int height) {
        return getNoiseMap2d(0, 0, width, height);
    }
    public float[][] getNoiseMap2d(int startX, int startY, int width, int height) {
        //System.out.println((int) ((float) Math.pow(2, 32)));
        startX += Math.pow(2, 30);//1073741824;
        startY += Math.pow(2, 30);//1073741824;
        this.startX = startX;
        this.startY = startY;
        result2d = new float[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                result2d[x][y] = getPerlinNoise2d(
                        (startX + x), 
                        (startY + y));
            }
        }
        float[][] map = result2d;
        result2d = null;
        return map;
    }
}
*/