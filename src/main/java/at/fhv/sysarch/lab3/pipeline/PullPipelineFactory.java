package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.*;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class PullPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        // TODO: pull from the source (model)
        PullSource pullSource = new PullSource();
        PullPipe<Face> pullPipe = new PullPipe<>(pullSource);

        // TODO 1. perform model-view transformation from model to VIEW SPACE coordinates
        PullModelViewTransformationFilter<Face> pullModelViewTransformationFilter = new PullModelViewTransformationFilter<>(pullPipe, pd);
        PullPipe<Face> toBackfaceCullingFilter = new PullPipe<>(pullModelViewTransformationFilter);

        // TODO 2. perform backface culling in VIEW SPACE
        PullBackfaceCullingFilter<Face> pullBackfaceCullingFilter = new PullBackfaceCullingFilter<>(toBackfaceCullingFilter);
        PullPipe<Face> toPullDepthSortingFilter = new PullPipe<>(pullBackfaceCullingFilter);

        // TODO 3. perform depth sorting in VIEW SPACE
        PullDepthSortingFilter<Face> pullDepthSortingFilter = new PullDepthSortingFilter<>(toPullDepthSortingFilter);
        PullPipe<Face> toPullColoringFilter = new PullPipe<>(pullDepthSortingFilter);

        // TODO 4. add coloring (space unimportant)
        PullColoringFilter<Face> pullColoringFilter = new PullColoringFilter<>(toPullColoringFilter, pd);
        PullPipe<Pair<Face, Color>> toProjectTransformationFilter = new PullPipe<>(pullColoringFilter);

        // lighting can be switched on/off
        if (pd.isPerformLighting()) {
            // 4a. TODO perform lighting in VIEW SPACE

            // 5. TODO perform projection transformation on VIEW SPACE coordinates
        } else {
            // 5. TODO perform projection transformation


        }
        PullProjectTransformationFilter<Pair<Face, Color>> pullProjectTransformationFilter = new PullProjectTransformationFilter<>(toProjectTransformationFilter, pd);
        PullPipe<Pair<Face, Color>> toPullScreenSpaceTransformationFilter = new PullPipe<>(pullProjectTransformationFilter);

        // TODO 6. perform perspective division to screen coordinates
        PullScreenSpaceTransformationFilter pullScreenSpaceTransformationFilter = new PullScreenSpaceTransformationFilter(toPullScreenSpaceTransformationFilter, pd);
        PullPipe<Pair<Face, Color>> afterScreenSpaceTransformation = new PullPipe<>(pullScreenSpaceTransformationFilter);

        // TODO 7. feed into the sink (renderer)


        // returning an animation renderer which handles clearing of the
        // viewport and computation of the fraction
        return new AnimationRenderer(pd) {

            // TODO rotation variable goes in here
            float totalRotation = 0;

            /** This method is called for every frame from the JavaFX Animation
             * system (using an AnimationTimer, see AnimationRenderer). 
             * @param fraction the time which has passed since the last render call in a fraction of a second
             * @param model    the model to render 
             */
            @Override
            protected void render(float fraction, Model model) {

                // TODO compute rotation in radians
                totalRotation += fraction;
                double rad = totalRotation % (2 * Math.PI);


                // TODO create new model rotation matrix using pd.getModelRotAxis and Matrices.rotate
                Mat4 rotationMatrix = Matrices.rotate((float) rad, pd.getModelRotAxis());

                // TODO compute updated model-view tranformation
                pullModelViewTransformationFilter.setRotationMatrix(rotationMatrix);

                // TODO update model-view filter
                pullSource.setFaces(model.getFaces());

                // TODO trigger rendering of the pipeline

                while (afterScreenSpaceTransformation.hasNext()) {
                    Pair<Face, Color> pair = afterScreenSpaceTransformation.pull();

                    var ctx = pd.getGraphicsContext();

                    ctx.setStroke(pair.snd());
                    ctx.setFill(pair.snd());

                    var cordX = new double[]{pair.fst().getV1().getX(), pair.fst().getV2().getX(), pair.fst().getV3().getX()};
                    var cordY = new double[]{pair.fst().getV1().getY(), pair.fst().getV2().getY(), pair.fst().getV3().getY()};


                    ctx.fillOval(cordX[0], cordY[0], 2, 2);


//                    pd.getGraphicsContext().strokeLine(face.getV1().getX(), face.getV1().getY(), face.getV2().getX(), face.getV2().getY());
//                    pd.getGraphicsContext().strokeLine(face.getV2().getX(), face.getV2().getY(), face.getV3().getX(), face.getV3().getY());
//                    pd.getGraphicsContext().strokeLine(face.getV3().getX(), face.getV3().getY(), face.getV1().getX(), face.getV1().getY());


                }
            }
        };
    }
}