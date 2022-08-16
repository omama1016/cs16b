package lab14;

import lab14lib.Generator;
import lab14lib.GeneratorAudioVisualizer;

public class StrangeBitwiseGenerator implements Generator{
    private int period;
    private int state;

    public StrangeBitwiseGenerator(int period) {
        this.period = period;
        state = 0;
    }

    @Override
    public double next() {
        state = (state + 1);
        int weirdState = state & (state >>> 3) % period;
        // int weirdState = state & (state >> 3) & (state >> 8) % period;
        // int weirdState = state & (state >> 7) % period;
        return normalize(weirdState % period);
    }

    private double normalize(int state) {
        return 2.0 / (period - 1) * state - 1;
    }

    public static void main(String[] args) {
        Generator generator = new StrangeBitwiseGenerator(512);
        GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
        gav.drawAndPlay(4096, 1000000);
    }
}
