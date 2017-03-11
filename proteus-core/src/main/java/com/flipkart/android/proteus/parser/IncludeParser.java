/*
 * Apache License
 * Version 2.0, January 2004
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION
 *
 * Copyright (c) 2017 Flipkart Internet Pvt. Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.flipkart.android.proteus.parser;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.flipkart.android.proteus.ProteusConstants;
import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.ViewTypeParser;
import com.flipkart.android.proteus.exceptions.ProteusInflateException;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.flipkart.android.proteus.value.Value;

/**
 * IncludeParser
 * <p>
 * TODO: merge the attributes into the included layout
 * </p>
 *
 * @author aditya.sharat
 */

public class IncludeParser<V extends View> extends ViewTypeParser<V> {

    @NonNull
    @Override
    public String getType() {
        return "include";
    }

    @Nullable
    @Override
    public String getParentType() {
        return null;
    }

    @NonNull
    @Override
    public ProteusView createView(@NonNull ProteusContext context, @NonNull Layout include, @NonNull ObjectValue data, @Nullable ViewGroup parent, int dataIndex) {

        if (include.extras == null) {
            throw new IllegalArgumentException("required attribute 'layout' missing.");
        }

        Value type = include.extras.get(ProteusConstants.LAYOUT);
        if (null == type || !type.isPrimitive()) {
            throw new ProteusInflateException("required attribute 'layout' missing or is not a string");
        }

        Layout layout = context.getLayout(type.getAsString());
        if (null == layout) {
            throw new ProteusInflateException("Layout '" + type + "' not found");
        }

        return context.getInflater().inflate(layout.merge(include), data, parent, dataIndex);
    }

    @Override
    protected void addAttributeProcessors() {

    }

}