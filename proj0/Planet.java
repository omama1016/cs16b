
public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
	static final double G = 6.67e-11;
    
	public Planet(double xP, double yP, double xV,
                  double yV, double m, String img){
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;
    }

    public Planet(Planet p) {
        this(p.xxPos, p.yyPos, p.xxVel, p.yyVel, p.mass, p.imgFileName);
    }
	
	public double calcDistance(Planet that){
        return Math.sqrt((this.xxPos-that.xxPos)*(this.xxPos-that.xxPos)
                        + (this.yyPos-that.yyPos)*(this.yyPos-that.yyPos));
    }
	
	public double calcForceExertedBy(Planet that){
        double r = calcDistance(that);
        return Planet.G*this.mass*that.mass/(r*r);
    }
	
	public double calcNetForceExertedByX(Planet[] ps){
        double total = 0;
        for (Planet p : ps) {
            if (!this.equals(p)){
                double force = this.calcForceExertedBy(p);
                double dis = this.calcDistance(p);
                double dx = p.xxPos - this.xxPos;
                total += force * dx / dis;
            }
        }
        return total;
    }

    public double calcNetForceExertedByY(Planet[] ps){
        double total = 0;
        for (Planet p : ps) {
            if (!this.equals(p)){
                double force = this.calcForceExertedBy(p);
                double dis = this.calcDistance(p);
                double dy = p.yyPos - this.yyPos;
                total += force * dy / dis;
            }
        }
        return total;
    }
	
	public void update(double dt, double fX, double fY){
        // compute the acc
        double aX = fX / this.mass;
        double yX = fY / this.mass;

        //calculate the squirrel’s new velocity
        this.xxVel += aX * dt;
        this.yyVel += yX * dt;

        //  calculate the new position of the squirrel
        this.xxPos += this.xxVel * dt;
        this.yyPos += this.yyVel * dt;
    }
}
