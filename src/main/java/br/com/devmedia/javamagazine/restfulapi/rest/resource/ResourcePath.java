/*
 * Copyright (C) 2012 Stormpath, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.devmedia.javamagazine.restfulapi.rest.resource;

import br.com.devmedia.javamagazine.restfulapi.model.bean.Comment;
import br.com.devmedia.javamagazine.restfulapi.model.bean.Post;
import br.com.devmedia.javamagazine.restfulapi.model.bean.User;
import br.com.devmedia.javamagazine.restfulapi.model.interfaces.Entity;

public enum ResourcePath {

    USERS(Link.USERS, User.class),
    POSTS(Link.POSTS, Post.class),
    COMMENTS(Link.COMMENTS, Comment.class);

    final String path;
    final Class<? extends Entity> associatedClass;

    private ResourcePath(String path, Class<? extends Entity> clazz) {
        this.path = path;
        this.associatedClass = clazz;
    }

    public static ResourcePath forClass(Class<? extends Entity> clazz) {
        for (ResourcePath rp : values()) {
            //Cannot use equals because of hibernate proxied object
            //Cannot use instanceof because type not fixed at compile time
            if (rp.associatedClass.isAssignableFrom(clazz)) {
                return rp;
            }
        }
        throw new IllegalArgumentException("No ResourcePath for class '" + clazz.getName() + "'");
    }

    public String getPath() {
        return path;
    }

    public Class<? extends Entity> getAssociatedClass() {
        return associatedClass;
    }
}
