/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

//
// This source code implements specifications defined by the Java
// Community Process. In order to remain compliant with the specification
// DO NOT add / change / or delete method signatures!
//

package javax.persistence;

import javax.persistence.metamodel.Attribute;
import java.util.List;

public interface EntityGraph<T> {

    public String getName();

    public void addAttributeNodes(String ... attributeName);

    public void addAttributeNodes(Attribute<T, ?> ... attribute);

    public <X> Subgraph<X> addSubgraph(Attribute<T, X> attribute);

    public <X> Subgraph<? extends X> addSubgraph(Attribute<T, X> attribute, Class<? extends X> type);

    public <X> Subgraph<X> addSubgraph(String attributeName);

    public <X> Subgraph<X> addSubgraph(String attributeName, Class<X> type);

    public <X> Subgraph<X> addKeySubgraph(Attribute<T, X> attribute);

    public <X> Subgraph<? extends X> addKeySubgraph(Attribute<T, X> attribute, Class<? extends X> type);

    public <X> Subgraph<X> addKeySubgraph(String attributeName);

    public <X> Subgraph<X> addKeySubgraph(String attributeName, Class<X> type);


    public <T> Subgraph<? extends T> addSubclassSubgraph(Class<? extends T> type);
//    public <S extends T> Subgraph<S> addSubclassSubgraph(Class<S> type);


    public List<AttributeNode<?>> getAttributeNodes();

}
