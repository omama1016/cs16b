package lab14;

import lab14lib.Generator;
import lab14lib.GeneratorAudioVisualizer;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private double factor;

    public AcceleratingSawToothGenerator(int period, double factor) {
        this.period = period;
        this.state = 0;
        this.factor = factor;
    }


    @Override
    public double next() {
        state += 1;

        if (state % period == 0) {
            period = (int) (period * factor);
            state = 0;
        }
        return normalize(state);
    }

    private double normalize(int num) {
        return -1 + 2.0 * (num % period) / (period - 1);
    }

    public static void main(String[] args) {
        Generator generator = new AcceleratingSawToothGenerator(200, 1.1);
        GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
        gav.drawAndPlay(4096, 1000000);

    }
}
