# Easy Animator

___

# Assignment 7: The Final One (unless we count the extra!)

- In assignment 7 we implemented the interactive view with its controller.

- The interactive view supports the various features such as start, pause, restart, resume, loop,
  and set speed.

- We also created a mock model and view to test the controllers.

- After Assignment 6.2 fixes, there was not much changed in our model. If anything, we might have
  slightly adjusted our visual view to reduce duplicate code since the interactive view extends it.
  That is the same for our interactive view controller.
  
- We used the subscriber/emitter pattern to design the interactive animation controller.

# ASSIGNMENT 6.2: FIXED ISSUES

- Fixed the issues and completed implementations left unfinished from last submission.

# ASSIGNMENT 6:

## Overview:

- For assignment 6, we have to make various changes to our models. We first adopted a new method
  when it came to our models. Our main models were MotionModel, ShapeModel, and AnimationModel. Each
  model also had a view associated with it.

- We ran into trouble last minute because the assignment requested some weird implementations we
  were not accounting for. That is, the use of a canvas.

- Our motions draw twice, and we know why, we could not implement the fix, but will do for the next
  assignment.

- Overall, we know why we have the certain bugs that we have, and already started implementing the
  fixes for them.

- Our SVG View is not complete because it is a bit hard to implement the animations, we almost have
  it done though.

- We already added our controllers, and that will help us get going in the next assignment.

- There are various changes that might not be documented here, but they improved our model from the
  previous assignment. We also mostly focused on simplifying some aspects of our old design.

___

# ASSIGNMENT 5:

Assignment 5 submission. Created by Sultan and Nop.

## Interfaces:

1. AnimatorModel: Represents the animator itself. It is where the client would be able to add shapes
   and motions. There are several methods to help facilitate adding, getting, and removing shapes
   and motions.

2. ShapeModel: Representation of a shape. A shape should be an object that can have some of its
   properties mutated. To be specific, the shape can be moved, scaled, or colored a different color.
   A observers are required to be able to translate this shape representation into another.

3. MotionModel :Represents a model for motions. Motions are animation instructions. A motion can be
   used to apply a certain instruction on a shape.

---

## Classes:

We created classes that implemented our models. However, we have three unique classes that don't
implement any interface that we designed.

1. FrameInterval: This represents intervals of frames. It helps when working with motions. It has
   several very useful methods too.

2. Location: It is a simple 2D-Coordinate representation for the location of the shapes.

3. AnimatedShape: This is the most important class. It allows a basic 2D shape to be animated by
   also holding information about its motions.

---

## Some Notes:

* Our representation for Motions is sadly not as good for the purposes of this assignment. As
  generic implementations, we learned from a TA that they are actually good. However, for the
  purposes of this assignment and what the assignment expects from us, it can be troublesome. That
  is way, we plan to change the implementation of motions by the next assignment.

* Our representation actually made it hard for us to implement toString(). The method does not work
  properly because our motions representation isn't the best for the purposes of the toString(). To
  be clear, we expect the code of toString() to be working correctly. There might be a small bug,
  that needs more testing, but we can't test until we fix our Motions class. Fixing our motions
  representations then leads to fixing our getShapeAt(int frame) method, which is what our
  toString() is using.