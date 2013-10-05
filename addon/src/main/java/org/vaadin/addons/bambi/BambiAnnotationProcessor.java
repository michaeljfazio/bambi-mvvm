package org.vaadin.addons.bambi;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * An experimental JSR269 annotation processor that is intended to be used to validate Bambi annotated bindings at
 * compile time.
 *
 * @author Michael Fazio
 */
@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes({
        "org.vaadin.addons.bambi.PropertyBound",
        "org.vaadin.addons.bambi.ItemBound",
        "org.vaadin.addons.bambi.ContainerBound",
        "org.vaadin.addons.bambi.EventBound",
        "org.vaadin.addons.bambi.ActionBound",
        "org.vaadin.addons.bambi.ValueChangeBound"
})
public class BambiAnnotationProcessor extends AbstractProcessor {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(PropertyBound.class);
        for(Element element: elements) {
            // TODO: [MF] Inspect element type and create error if annotation is invalid.
        }
        return true;
    }

}