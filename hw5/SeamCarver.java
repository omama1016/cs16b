import edu.princeton.cs.algs4.Picture;

import java.util.Arrays;

public class SeamCarver {
    private int[][] energies;
    private Picture picture;
    private int[] verticalSeam;
    private int[] horizontalSeam;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        energies = new int[picture.width()][picture.height()];

        // cal the energies
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                energies[i][j] = computeEnergy(i, j, picture);
            }
        }
        verticalSeam = calMinCost(energies);
        int[][] transEnergies = transpose(energies);
        horizontalSeam = calMinCost(transEnergies);
    }
    // current picture
    public Picture picture() {
        return picture;
    }
    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }
    // energy of pixel at column x and row y
    public  double energy(int x, int y) {
        if (isOutOfScope(x, y, energies)) {
            throw new IndexOutOfBoundsException("Out of index");
        }
        return energies[x][y];
    }

    public   int[] findHorizontalSeam(){
        return horizontalSeam;
    }

    public   int[] findVerticalSeam() {
        return verticalSeam;
    }

    public    void removeHorizontalSeam(int[] seam) {
        if (seam.length != width()) throw new IllegalArgumentException("wrong input...");
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i+1] - seam[i]) > 1) throw new IllegalArgumentException("wrong input...");
        }

        SeamRemover.removeHorizontalSeam(picture, horizontalSeam);
    }
    public    void removeVerticalSeam(int[] seam) {
        if (seam.length != height()) throw new IllegalArgumentException("wrong input...");
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i+1] - seam[i]) > 1) throw new IllegalArgumentException("wrong input...");
        }

        SeamRemover.removeVerticalSeam(picture, verticalSeam);
    }

    // private methods
    private boolean isOutOfScope(int x, int y, int[][] matrx) {
        if (x < 0 || x >= matrx.length || y < 0 || y >= matrx[0].length) {
            return true;
        }
        return false;
    }

    // compute energy of a specific pixel
    private int computeEnergy(int x, int y, Picture picture) {
        int leftX = x - 1 >= 0 ? x - 1 : picture.width() - 1;
        int rightX = x + 1 <= picture.width() - 1 ? x + 1 : 0;
        int upY = y - 1 >= 0 ? y - 1 : picture.height() - 1;
        int downY = y + 1 <= picture.height() - 1 ? y + 1 : 0;

        int deltaXRed = Math.abs( picture.get(rightX, y).getRed() - picture.get(leftX, y).getRed());
        int deltaXGreen = Math.abs( picture.get(rightX, y).getGreen() - picture.get(leftX, y).getGreen());
        int deltaXBlue = Math.abs( picture.get(rightX, y) .getBlue() - picture.get(leftX, y).getBlue());
        int deltaYRed = Math.abs( picture.get(x, downY) .getRed() - picture.get(x, upY).getRed());
        int deltaYGreen = Math.abs( picture.get(x, downY) .getGreen() - picture.get(x, upY).getGreen());
        int deltaYBlue = Math.abs( picture.get(x, downY) .getBlue() - picture.get(x, upY).getBlue());

        return deltaXRed*deltaXRed + deltaXGreen*deltaXGreen + deltaXBlue*deltaXBlue
                + deltaYRed*deltaYRed + deltaYGreen*deltaYGreen + deltaYBlue*deltaYBlue;

    }

    private int[][] transpose (int[][] origin) {
        // trans
        int[][] trans = new int[origin[0].length][origin.length];
        for (int i = 0; i < origin.length; i++) {
            for (int j = 0; j < origin[0].length; j++) {
                trans[j][i] = origin[i][j];
            }
        }
        return trans;
    }

    private int[] calMinCost(int[][] energies){

        int[][] minCost = new int[energies.length][energies[0].length];
        int[][] paths = new int[energies.length][energies[0].length];

        for (int j = 0; j < energies.length; j++) {
            minCost[j][0] = energies[j][0];
            paths[j][0] = -1;
        }

        for (int i = 1; i < energies[0].length; i++) {
            for (int j = 0; j < energies.length; j++) {
                int minTopLeft = Integer.MAX_VALUE;
                int minTopRight = Integer.MAX_VALUE;
                int minTop = Integer.MAX_VALUE;
                if (!isOutOfScope(j-1, i-1, energies)) minTopLeft = minCost[j-1][i-1];
                if (!isOutOfScope(j, i-1, energies)) minTop = minCost[j][i-1];
                if (!isOutOfScope(j+1, i-1, energies)) minTopRight = minCost[j+1][i-1];

                int minValue = Integer.MAX_VALUE;
                int minIndex = -1;
                if (minTopLeft < minValue) {
                    minValue = minTopLeft;
                    minIndex = j - 1;
                }
                if (minTop < minValue) {
                    minValue = minTop;
                    minIndex = j;
                }

                if (minTopRight < minValue) {
                    minValue = minTopRight;
                    minIndex = j + 1;
                }

                paths[j][i] = minIndex;
                minCost[j][i] = energies[j][i] + minValue;
            }
        }

        // find vertical seam
        int buttomMinIndex = -1;
        int minValue = Integer.MAX_VALUE;
        for (int i = 0; i < minCost.length; i++) {
            if (minCost[i][minCost[0].length-1] < minValue) {
                minValue = minCost[i][minCost[0].length-1];
                buttomMinIndex = i;
            }
        }
        int[] vertical = new int[energies[0].length];
        for (int i = energies[0].length - 1; i >= 0; i--) {
            vertical[i] = buttomMinIndex;
            buttomMinIndex = paths[buttomMinIndex][i];
        }

        return vertical;
    }
}
