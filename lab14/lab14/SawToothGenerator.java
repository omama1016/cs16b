package lab14;

import lab14lib.Generator;
import lab14lib.GeneratorAudioVisualizer;
import lab14lib.GeneratorDrawer;

public class SawToothGenerator implements Generator {
    private int period;
    private int state;

    public SawToothGenerator(int period) {
        this.period = period;
        state = 0;
    }

    @Override
    public double next() {
        state = (state + 1);
        return normalize(state % period);
    }

    private double normalize(int state) {
        return 2.0 / (period - 1) * state - 1;
    }

    public static void main(String[] args) {
        Generator generator = new SawToothGenerator(512);
        GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
        gav.drawAndPlay(4096, 1000000);
    }
}
