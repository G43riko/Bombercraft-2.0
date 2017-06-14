package utils.math;

public final class GColision {
	/*
	 * 2D COLLISIONS 
	 */
	
	/**
	 * Rectangle - Rectangle collision
	 */
	
	public static boolean rectRectCollision(float ax, float ay, float aw, float ah, float bx, float by, float bw, float bh){
		return (bx + bw > ax) && (by + bh > ay) && (bx < ax + aw) && (by < ay + ah);
	};
	
	/**
	 * Circle - Circle collision
	 */
	
	public static boolean circleCircleCollision(float ax, float ay, float aradius, float bx, float by, float beadius){
		return circleCircleCollision(new GVector2f(ax,ay),aradius,new GVector2f(bx,by),beadius);
	};
	
	public static boolean circleCircleCollision(GVector2f a, float aradius, GVector2f b, float bradius){
		double dist =a.dist(b);
		return dist <= aradius + bradius;
	}
	
	/**
	 * Point - Rectangle collision
	 */
	
	public static boolean pointRectCollision(float ax, float ay, float awidth, float aheight, float bx,float by){
		return pointRectCollision(new GVector2f(ax,ay), new GVector2f(awidth,aheight), new GVector2f(bx,by));
	};
	
	public static boolean pointRectCollision(GVector2f aPos, GVector2f aSize, GVector2f bPos){
		return bPos.getX() > aPos.getX() && 
			   bPos.getX() < aPos.getX() + aSize.getX() && 
			   bPos.getY() > aPos.getY() && 
			   bPos.getY() < aPos.getY() + aSize.getY();
	};
	
	/**
	 * Point - Circle collision
	 */
	
	public static boolean pointCircleCollision(float ax, float ay, float bx, float by, float bradius){
		return pointCircleCollision(new GVector2f(ax,ay),new GVector2f(bx,by),bradius);
	};
	
	public static boolean pointCircleCollision(GVector2f a, GVector2f b, float bradius){
		return a.dist(b)<bradius;
	};
	
	/*
//	 * 3D COLLISIONS 
//	 */
//	
//	/**
//	 * Sphere - Sphere collision
//	 */
//	
//	public static boolean sphereSphereCollision(float ax, float ay, float az, float aradius, float bx, float by, float bz, float bradius){
//		return sphereSphereCollision(new GVector3f(ax, ay, az), aradius, new GVector3f(bx, by, bz), bradius);
//	}
//	
//	public static boolean sphereSphereCollision(GVector3f posA, float radiusA, GVector3f posB, float radiusB){
//		return posA.dist(posB) < radiusA + radiusB;
//	}
//	
//	/**
//	 * Point - Sphere collision
//	 */
//	
//	public static boolean pointSphereCollision(float ax, float ay, float az, float bx, float by, float bz, float bradius){
//		return pointSphereCollision(new GVector3f(ax, ay, az), new GVector3f(bx, by, bz), bradius);
//	};
//	
//	public static boolean pointSphereCollision(GVector3f a, GVector3f b, float bradius){
//		return a.dist(b)<bradius;
//	};
//	
//	/**
//	 * Line - Sphere collision
//	 */
//	
//	public static boolean lineSphereCollision(float ax, float ay, float az, float bx, float by, float bz, float sx, float sy, float sz, float sradius){
//		return lineSphereCollision(new GVector3f(ax, ay, az), new GVector3f(bx, by, bz), new GVector3f(sx, sy, sz), sradius);
//	}
//	
//	public static boolean lineSphereCollision(GVector3f pointA, GVector3f pointB,  GVector3f sphereP, float sphereRadius){
//		return GMath.distPointLine(pointA, pointB, sphereP) < sphereRadius;
//	}
//	
//	/**
//	 * Point - Box collision
//	 */
//	
//	public static boolean pointBoxCollision(float ax, float ay, float az, float awidth, float aheight,float adepth, float bx,float by, float bz){
//		return pointBoxCollision(new GVector3f(ax, ay, az), new GVector3f(awidth, aheight, adepth), new GVector3f(bx, by, bz));
//	}
//	
//	public static boolean pointBoxCollision(GVector3f  a, GVector3f asize, GVector3f  b){
//		//neskontrolovane
//		return a.getX() < b.getX() && a.getX() + asize.getX() > b.getX() &&
//			   a.getY() < b.getY() && a.getY() + asize.getY() > b.getY() &&
//			   a.getZ() < b.getZ() && a.getZ() + asize.getZ() > b.getZ();
//	}
//
//	/**
//	 * Line - Box collision
//	 */
//	
//	public static boolean lineBoxCollision(GVector3f a1, GVector3f a2, GVector3f bpos, GVector3f bsize){
//		/*  
//		 *   E-------F
//		 *  /|      /|
//		 * A-+-----B |
//		 * | |     | |
//		 * | H-----+-G
//		 * |/      |/
//		 * D-------C
//		 */
////		bsize = bsize.div(2);
////		bpos = bpos.sub(bsize.div(2));
//		/*
//		 * predok
//		 * zadok
//		 * pravo
//		 * lavo
//		 * vrsok
//		 * spodok
//		 */
//		return GVector3f.intersectRayWithSquare(a1, a2, bpos.add(bsize.mul(-1, 1,-1)), bpos.add(bsize.mul(-1,-1,-1)), bpos.add(bsize.mul( 1,-1,-1))) ||
//			   GVector3f.intersectRayWithSquare(a1, a2, bpos.add(bsize.mul(-1, 1, 1)), bpos.add(bsize.mul(-1,-1, 1)), bpos.add(bsize.mul( 1,-1, 1))) ||
//			   
//			   GVector3f.intersectRayWithSquare(a1, a2, bpos.add(bsize.mul( 1, 1, 1)), bpos.add(bsize.mul( 1,-1, 1)), bpos.add(bsize.mul( 1, 1,-1))) ||
//			   GVector3f.intersectRayWithSquare(a1, a2, bpos.add(bsize.mul(-1, 1, 1)), bpos.add(bsize.mul(-1,-1, 1)), bpos.add(bsize.mul(-1, 1,-1))) ||
//			   
//			   GVector3f.intersectRayWithSquare(a1, a2, bpos.add(bsize.mul( 1, 1, 1)), bpos.add(bsize.mul(-1, 1, 1)), bpos.add(bsize.mul( 1, 1,-1))) ||
//			   GVector3f.intersectRayWithSquare(a1, a2, bpos.add(bsize.mul( 1,-1, 1)), bpos.add(bsize.mul(-1,-1, 1)), bpos.add(bsize.mul( 1,-1,-1)));
//	}
//	
//	/**
//	 * Box - Box collision
//	 */
//	
//	public static boolean boxBoxCollision(float ax, float ay, float az, float aw, float ah, float ad, float bx, float by, float bz, float bw, float bh, float bd){
//		return boxBoxCollision(new GVector3f(ax, ay, az), new GVector3f(aw, ah, ad), new GVector3f(bx, by, bz), new GVector3f(bw, bh, bd));
//	};
//	
//	public static boolean boxBoxCollision(GVector3f apos, GVector3f asize, GVector3f bpos, GVector3f bsize){
//		return apos.getX() + asize.getX() > bpos.getX() && bpos.getX() + bsize.getX() > apos.getX() &&
//			   apos.getY() + asize.getY() > bpos.getY() && bpos.getY() + bsize.getY() > apos.getY() &&
//			   apos.getZ() + asize.getZ() > bpos.getZ() && bpos.getZ() + bsize.getZ() > apos.getZ();
//	};
//
//	/**
//	 * Point - Ellipsoid collision
//	 */
//	
//	public static boolean pointEllipsoidCollision(float ax, float ay, float az, float bx, float by, float bz, float ba, float bb, float bc){
//		return pointEllipsoidCollision(new GVector3f(ax, ay, az), new GVector3f(bx, by, bz), new GVector3f(ba, bb, bc));
//	}
//	
//	public static boolean pointEllipsoidCollision(GVector3f a, GVector3f b, GVector3f bsize){
//		GVector3f aposNew = a.sub(b);
//		
//		float xa = (aposNew.getX() * aposNew.getX()) / (bsize.getX() * bsize.getX());
//		float yb = (aposNew.getY() * aposNew.getY()) / (bsize.getY() * bsize.getY());
//		float zc = (aposNew.getZ() * aposNew.getZ()) / (bsize.getZ() * bsize.getZ());
//		
//		return xa + yb + zc <= 1;
//	}
//
//	/**
//	 * Line - Ellipsoid collision
//	 */
//	
//	public static boolean lineEllipsoidCollision(GVector3f a, GVector3f b, GVector3f e, GVector3f esize){
//		GVector3f point = GMath.getClosestPointOnSegment(a, b, e);
//		return pointEllipsoidCollision(point, e, esize);
//	}
//	
//	/**
//	 * Point - Cylinder collision
//	 */
//	
//	public static boolean pointCylinderCollision(float ax, float ay, float az, float bx, float by, float bz, float bradius, float bheight){
//		return pointCylinderCollision(new GVector3f(ax, ay, az), new GVector3f(bx, by, bz), bradius, bheight);
//	}
//	
//	public static boolean pointCylinderCollision(GVector3f a, GVector3f b, float bradius, float bheight){
//		boolean conditionOne = a.getY() > b.getY() && a.getY() < b.getY() + bheight;
//		boolean conditionTwo = a.getXZ().dist(b.getXZ()) < bradius;
//		
//		return conditionOne && conditionTwo;
//	}
//	
//	/**
//	 * Sphere - Cylinder collision
//	 */
//	
//	public static boolean sphereCylinderCollision(float ax, float ay, float az, float aradius, float bx, float by, float bz, float bradius, float bheight){
//		//NEPRESNE
//		return sphereCylinderCollision(new GVector3f(ax, ay, az), aradius, new GVector3f(bx, by, bz), bradius, bheight);
//	}
//	
//	public static boolean sphereCylinderCollision(GVector3f a, float aradius, GVector3f b, float bradius, float bheight){
//		//NEPRESNE
//		boolean conditionOne = a.getY() + aradius > b.getY() && a.getY() - aradius < b.getY() + bheight;
//		boolean conditionTwo = a.getXZ().dist(b.getXZ()) < aradius + bradius;
//		
//		return conditionOne && conditionTwo;
//	}
}
