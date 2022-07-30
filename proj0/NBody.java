public class NBody {

    // return the radius of the planet
    public static double readRadius(String filename){
        In in = new In(filename);
        int numOfPlanets = in.readInt();
        double radius = in.readDouble();
        return  radius;
    }

    // return a array of all planets specified in the file
    public static Planet[] readPlanets(String filename){
        In in = new In(filename);
        int numOfPlanets = in.readInt();
        double radius = in.readDouble();
        Planet[] ps = new Planet[numOfPlanets];
        for (int i=0; i<ps.length; i++) {
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double mass = in.readDouble();
            String images = in.readString();
            ps[i] = new Planet(xP, yP, xV, yV, mass, images);
        }
        return ps;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        // draw background
        StdDraw.setXscale(-5e11, 5e11);
        StdDraw.setYscale(-5e11, 5e11);

        StdDraw.enableDoubleBuffering();

        Planet[] ps = readPlanets(filename);
        double radius = readRadius(filename);

        for (double t = 0; t < T; t = t+dt){
            StdDraw.clear();
            double[] xForces = new double[ps.length];
            double[] yForces = new double[ps.length];
            for (int i = 0; i < ps.length; i++) {
                //StdDraw.point(p.xxPos, p.yyPos);
                xForces[i] = ps[i].calcNetForceExertedByX(ps);
                yForces[i] = ps[i].calcNetForceExertedByY(ps);
            }
            for (int i = 0; i < ps.length; i++) {
                ps[i].update(dt, xForces[i], yForces[i]);
            }

            for (Planet p : ps) {
                StdDraw.picture(p.xxPos, p.yyPos, "images/" + p.imgFileName);
            }

            StdDraw.show();
            StdDraw.pause(10);
        }

        StdOut.printf("%d\n", ps.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < ps.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    ps[i].xxPos, ps[i].yyPos, ps[i].xxVel,
                    ps[i].yyVel, ps[i].mass, ps[i].imgFileName);
        }
    }
}

