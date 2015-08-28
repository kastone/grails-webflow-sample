package org.universe.research

import org.grails.databinding.converters.ValueConverter

/**
 * Created by kstoneDev on 7/17/2015.
 */
class CodeValueConverter implements ValueConverter{
    @Override
    boolean canConvert(value){
        value instanceof String
    }

    @Override
    def convert(Object value) {
        PlanetSize.findByCode(value)
    }

    @Override
    Class<?> getTargetType(){
        //specifies the type to which this converter may be applied
        PlanetSize
    }
}
