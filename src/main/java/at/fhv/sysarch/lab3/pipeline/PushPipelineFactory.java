package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.*;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collection;

public class PushPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {

        // TODO: the connection of filters and pipes requires a lot of boilerplate code. Think about options how this can be minimized
        // Boilerplate code can be minimized by instantiating the filters and pipes in reverse

        // TODO 7. feed into the sink (renderer)
        PushRenderer pushRenderer = new PushRenderer(pd);
        PushPipe<Pair<Face, Color>> toPushRenderer = new PushPipe<>(pushRenderer);

        // TODO 6. perform perspective division to screen coordinates
        PushScreenSpaceTransformation pushScreenSpaceTransformation = new PushScreenSpaceTransformation(toPushRenderer, pd);
        PushPipe<Pair<Face, Color>> toPushScreenSpaceTransformation = new PushPipe<>(pushScreenSpaceTransformation);

        // 5. TODO perform projection transformation on VIEW SPACE coordinates
        PushProjectTransformationFilter pushProjectTransformationFilter = new PushProjectTransformationFilter(toPushScreenSpaceTransformation, pd);

        // lighting can be switched on/off
        PushPipe<Pair<Face, Color>> toTransformOrLightning;

        if (pd.isPerformLighting()) {
            // 4a. TODO perform lighting in VIEW SPACE
            PushPipe<Pair<Face, Color>> toPushProjectTransformationFilter = new PushPipe<>(pushProjectTransformationFilter);
            PushLightningFilter pushLightningFilter = new PushLightningFilter(toPushProjectTransformationFilter, pd);

            toTransformOrLightning = new PushPipe<>(pushLightningFilter);

        } else {
            toTransformOrLightning = new PushPipe<>(pushProjectTransformationFilter);
        }

        // TODO 4. add coloring (space unimportant)
        PushColoringFilter pushColoringFilter = new PushColoringFilter(toTransformOrLightning, pd);
        PushPipe<Face> toPushColoringFilter = new PushPipe<>(pushColoringFilter);

        // TODO 3. perform depth sorting in VIEW SPACE
        // not possible to implement reasonably

        // TODO 2. perform backface culling in VIEW SPACE
        PushBackfaceCullingFilter pushBackfaceCullingFilter = new PushBackfaceCullingFilter(toPushColoringFilter);
        PushPipe<Face> toPushBackfaceCullingFilter = new PushPipe<>(pushBackfaceCullingFilter);

        // TODO 1. perform model-view transformation from model to VIEW SPACE coordinates
        PushModelViewTransformationFilter pushModelViewTransformationFilter = new PushModelViewTransformationFilter(toPushBackfaceCullingFilter, pd);
        PushPipe<Face> toPushModelViewTransformationFilter = new PushPipe<>(pushModelViewTransformationFilter);

        // TODO: push from the source (model)
        PushSource pushSource = new PushSource(toPushModelViewTransformationFilter);

        // returning an animation renderer which handles clearing of the
        // viewport and computation of the praction
        return new AnimationRenderer(pd) {
            // TODO rotation variable goes in here
            private int pos = 0;

            /** This method is called for every frame from the JavaFX Animation
             * system (using an AnimationTimer, see AnimationRenderer). 
             * @param fraction the time which has passed since the last render call in a fraction of a second
             * @param model    the model to render 
             */
            @Override
            protected void render(float fraction, Model model) {
                // TODO compute rotation in radians

                // TODO create new model rotation matrix using pd.modelRotAxis

                // TODO compute updated model-view tranformation
                pushModelViewTransformationFilter.setRotationMatrix(rotationMatrix);

                // TODO update model-view filter
                // TODO trigger rendering of the pipeline
                pushSource.setFaces(model.getFaces());

                // line
                pd.getGraphicsContext().setStroke(Color.PINK);
                pd.getGraphicsContext().strokeLine(0+pos,0+pos,100+pos,100+pos);
                pos++;

                source.write(model);
            }
        };
    }
}