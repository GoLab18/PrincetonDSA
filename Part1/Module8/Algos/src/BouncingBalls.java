import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdDraw;

public class BouncingBalls {
    public static class CollisionSystem {
        private MinPQ<Event> pq;
        private double t = 0.0; // simulation clock
        private final Particle[] particles;

        public CollisionSystem(Particle[] particles) {
            this.particles = particles;
        }

        private void predict(Particle a) {
            if (a == null) return;

            for (Particle particle : particles) {
                double dt = a.timeToHit(particle);
                pq.insert(new Event(t + dt, a, particle));
            }

            pq.insert(new Event(t + a.timeToHitVerticalWall(), a, null));
            pq.insert(new Event(t + a.timeToHitHorizontalWall(), null, a));
        }

        private void redraw() {

        }

        public void simulate() {
            pq = new MinPQ<Event>();

            for (Particle p : particles) predict(p);
            pq.insert(new Event(0, null, null));

            while (!pq.isEmpty()) {
                Event event = pq.delMin();
                if (!event.isValid()) continue;

                Particle a = event.a;
                Particle b = event.b;

                for (Particle p : particles) p.move(event.time - t);

                t = event.time;

                if (a != null && b != null) a.bounceOff(b);
                else if (a != null && b == null) a.bounceOffVerticalWall();
                else if (a == null && b != null) b.bounceOffHorizontalWall();
                else if (a == null && b == null) redraw();

                predict(a);
                predict(b);
            }
        }
    }

    public static class Particle {
        private double rx, ry; // Position
        private double vx, vy; // Velocity
        private int count; // Number of collisions
        private final double radius;
        private final double mass;

        public Particle() {

        }

        public void move(double dt) {
            if ((rx + vx*dt < radius) || (rx + vx*dt > 1.0 - radius)) vx = -vx;
            if ((ry + vy*dt < radius) || (ry + vy*dt > 1.0 - radius)) vy = -vy;

            rx = rx + vx*dt;
            ry = ry + vy*dt;
        }

        public void draw() {
            StdDraw.filledCircle(rx, ry, radius);
        }

        public double timeToHit(Particle that) {
            if (this == that) return Double.POSITIVE_INFINITY;

            double dx = that.rx - this.rx, dy = that.ry - this.ry;
            double dvx = that.vx - this.vx, dvy = that.vy - this.vy;
            double dvdr = dx*dvx + dy*dvy;

            if (dvdr > 0) return Double.POSITIVE_INFINITY;

            double dvdv = dvx*dvx + dvy*dvy;
            double drdr = dx*dx + dy*dy;
            double sigma = this.radius - that.radius;
            double d = (dvdr*dvdr) - dvdv * (drdr - sigma*sigma);

            if (d < 0) return Double.POSITIVE_INFINITY;

            return -(dvdr + Math.sqrt(d)) / dvdv;
        }

        public void bounceOff(Particle that) {
            double dx = that.rx - this.rx, dy = that.ry - this.ry;
            double dvx = that.vx - this.vx, dvy = that.vy - this.vy;
            double dvdr = dx*dvx + dy*dvy;

            double dist = this.radius - that.radius;

            double J = 2 * this.mass * that.mass * dvdr / ((this.mass + that.mass) * dist);
            double Jx = J * dx / dist;
            double Jy = J * dy / dist;

            this.vx += Jx / this.mass;
            this.vy += Jy / this.mass;
            that.vx -= Jx / that.mass;
            that.vy -= Jy / that.mass;

            this.count++;
            that.count++;
        }
    }

    // Neither particle null -> particle-particle collision
    // One particle null -> particle-wall collision
    // Both particles null -> event redraw
    public static class Event implements Comparable<Event> {
        private double time; // Event time
        private Particle a, b; // Particles involved
        private int countA, countB; // Collision counts

        public Event(double t, Particle a, Particle b) {

        }

        public boolean isValid() {
            
        }

        @Override
        public int compareTo(Event that) {
            return (int) (this.time - that.time);
        }
    }
}
