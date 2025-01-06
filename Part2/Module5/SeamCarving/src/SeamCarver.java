import edu.princeton.cs.algs4.Picture;

import java.util.Arrays;

public class SeamCarver {
    private Picture picture;
    private double[][] energyGrid;

    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException("Constructor argument can't null");

        this.picture = new Picture(picture);
        calcEnergyGrid();
    }

    private void calcEnergyGrid() {
        int h = height(), w = width();

        energyGrid = new double[h][w];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (y == 0 || y == h-1 || x == 0 || x == w -1) energyGrid[y][x] = 1000;
                else dualGradientEnergyForPixelCalc(y, x, picture, energyGrid);
            }
        }
    }

    private void dualGradientEnergyForPixelCalc(int y, int x, Picture pic, double[][] grid) {
        int rgbRight = pic.get(x, y+1).getRGB();
        int rgbLeft = pic.get(x, y-1).getRGB();
        int rgbTop = pic.get(x-1, y).getRGB();
        int rgbBottom = pic.get(x+1, y).getRGB();

        grid[y][x] = Math.sqrt(
                Math.pow((((rgbRight >> 16) & 0xFF) - ((rgbLeft >> 16) & 0xFF)), 2)
                + Math.pow((((rgbRight >> 8) & 0xFF) - ((rgbLeft >> 8) & 0xFF)), 2)
                + Math.pow(((rgbRight & 0xFF) - (rgbLeft & 0xFF)), 2)
                + Math.pow((((rgbBottom >> 16) & 0xFF) - ((rgbTop >> 16) & 0xFF)), 2)
                + Math.pow((((rgbBottom >> 8) & 0xFF) - ((rgbTop >> 8) & 0xFF)), 2)
                + Math.pow(((rgbBottom & 0xFF) - (rgbTop & 0xFF)), 2)
        );
    }

    public Picture picture() {
        return new Picture(picture);
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public double energy(int x, int y) {
        if (!(x >= 0 && y >= 0 && x < energyGrid[0].length && y < energyGrid.length)) {
            throw new IllegalArgumentException("x and/or y value/s out of range");
        }

        return energyGrid[y][x];
    }

    public int[] findHorizontalSeam() {
        return findSeam(false);
    }

    public int[] findVerticalSeam() {
        return findSeam(true);
    }

    private int[] findSeam(boolean isVertical) {
        int fs = isVertical ? height() : width();
        int ss = isVertical ? width() : height();

        int[] seam = new int[fs];

        if (fs <= 2 || ss <= 2) {
            Arrays.fill(seam, 0);
            return seam;
        }

        double[][] distTo = new double[fs][ss];
        int[][] vertexTo = new int[fs][ss];

        Arrays.fill(distTo[0], 0.0);
        for (int i = 1; i < fs; i++) {
            Arrays.fill(distTo[i], Double.POSITIVE_INFINITY);
            Arrays.fill(vertexTo[i], -1);
        }

        apsp(distTo, vertexTo, fs, ss, isVertical);

        int idx = -1;
        double minEnergy = Double.POSITIVE_INFINITY;

        for (int i = 0; i < ss; i++) if (distTo[fs-1][i] < minEnergy) {
            minEnergy = distTo[fs-1][i];
            idx = i;
        }

        for (int i = fs-1; i >= 0; i--) {
            seam[i] = idx;
            idx = vertexTo[i][idx];
        }

        return seam;
    }

    private void apsp(double[][] distTo, int[][] vertexTo, int fs, int ss, boolean isVertical) {
        for (int i = 0; i < fs-1; i++) {
            for (int j = 0; j < ss; j++) {
                if (isVertical) {
                    if (j != 0) relax(true, distTo, vertexTo, i, j, -1);
                    relax(true, distTo, vertexTo, i, j, 0);
                    if (j != ss-1) relax(true, distTo, vertexTo, i, j, 1);
                } else {
                    if (j != 0) relax(false, distTo, vertexTo, i, j, -1);
                    relax(false, distTo, vertexTo, i, j, 0);
                    if (j != ss-1) relax(false, distTo, vertexTo, i, j, 1);
                }
            }
        }
    }

    private void relax(boolean isVertical, double[][] distTo, int[][] vertexTo, int i, int j, int jOffset) {
        double energy = isVertical ? energyGrid[i][j] : energyGrid[j][i];

        int ni = i + 1, nj = j + jOffset;

        if (ni < distTo.length && nj >= 0 && nj < distTo[0].length) {
            if (distTo[ni][nj] > distTo[i][j] + energy) {
                distTo[ni][nj] = distTo[i][j] + energy;
                vertexTo[ni][nj] = j;
            }
        }
    }

    public void removeHorizontalSeam(int[] seam) {
        removeSeam(seam, false);
    }

    public void removeVerticalSeam(int[] seam) {
        removeSeam(seam, true);
    }

    private void removeSeam(int[] seam, boolean isVertical) {
        if (seam == null) throw new IllegalArgumentException("Seam can't null");
        validatePicSize(isVertical);
        validateSeam(seam, isVertical);

        int fs = isVertical ? height() : width();
        int ss = isVertical ? width() : height();


        Picture np = new Picture(isVertical ? width() - 1 : width(), isVertical ? height() : height() - 1);
        double[][] neg = new double[np.height()][np.width()];

        for (int i = 0; i < fs; i++) {
            int j = 0;
            while (j != seam[i]) {
                if (isVertical) np.set(j, i, picture.get(j, i));
                else np.set(i, j, picture.get(i, j));

                j++;
            }

            j++;

            while (j < ss) {
                if (isVertical) np.set(j-1, i, picture.get(j, i));
                else np.set(i, j-1, picture.get(i, j));
                j++;
            }
        }

        int nfs = isVertical ? np.height() : np.width();
        int nss = isVertical ? np.width() : np.height();

        for (int i = 0; i < nfs; i++) {
            for (int j = 0; j < nss; j++) {
                int ci = isVertical ? i : j, cj = isVertical ? j : i;

                if (i == 0 || i == nfs - 1 || j == 0 || j == nss - 1) neg[ci][cj] = 1000;

                else {
                    if (j == seam[i] || j == seam[i] - 1 || j == seam[i] + 1) {
                        dualGradientEnergyForPixelCalc(ci, cj, np, neg);
                        continue;
                    }

                    neg[ci][cj] = energyGrid[ci][cj];
                }
            }
        }

        picture = np;
        energyGrid = neg;
    }

    private void validatePicSize(boolean isVertical) {
        int s = isVertical ? width() : height();
        if (s <= 1) throw new IllegalArgumentException(
                "Picture's" + (isVertical ? "width" : "height") + " can't be less than or equal to 1");
    }

    private void validateSeam(int[] seam, boolean isVertical) {
        int fs = isVertical ? height() : width();
        int ss = isVertical ? width() : height();

        if (seam.length != fs) throw new IllegalArgumentException("Seam has a wrong length");

        for (int v : seam) if (v < 0 || v >= ss) {
            throw new IllegalArgumentException("Seam has values outside of its prescribed range");
        }

        for (int i = 0; i < fs-1; i++) if (Math.abs(seam[i] - seam[i+1]) > 1) {
                throw new IllegalArgumentException("Seam can't have two adjacent entries that differ by more than 1");
        }
    }

    public static void main(String[] args) {
        Picture pic = new Picture(args[0]);

        SeamCarver carver1 = new SeamCarver(pic);
        int[] seam1 = carver1.findVerticalSeam();
        System.out.println(Arrays.toString(seam1));
        carver1.removeVerticalSeam(seam1);

        SeamCarver carver2 = new SeamCarver(pic);
        int[] seam2 = carver2.findHorizontalSeam();
        System.out.println(Arrays.toString(seam2));
        carver2.removeHorizontalSeam(seam2);


        carver1.picture().show();
        carver2.picture().show();
    }
}