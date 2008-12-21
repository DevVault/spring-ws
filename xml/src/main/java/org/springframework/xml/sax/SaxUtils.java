/*
 * Copyright 2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.xml.sax;

import java.io.IOException;
import java.net.URISyntaxException;

import org.xml.sax.InputSource;

import org.springframework.core.io.Resource;

/**
 * Convenient utility methods for dealing with SAX.
 *
 * @author Arjen Poutsma
 * @since 1.0.0
 */
public abstract class SaxUtils {

    /**
     * Creates a SAX <code>InputSource</code> from the given resource. Sets the system identifier to the resource's
     * <code>URL</code>, if available.
     *
     * @param resource the resource
     * @return the input source created from the resource
     * @throws IOException if an I/O exception occurs
     * @see InputSource#setSystemId(String)
     * @see #getSystemId(org.springframework.core.io.Resource)
     */
    public static InputSource createInputSource(Resource resource) throws IOException {
        InputSource inputSource = new InputSource(resource.getInputStream());
        inputSource.setSystemId(getSystemId(resource));
        return inputSource;
    }

    /** Retrieves the URL from the given resource as System ID. Returns <code>null</code> if it cannot be openened. */
    public static String getSystemId(Resource resource) {
        try {
            return resource.getURL().toURI().toString();
        }
        catch (IOException e) {
            return null;
        }
        catch (URISyntaxException e) {
            return null;
        }
    }

}