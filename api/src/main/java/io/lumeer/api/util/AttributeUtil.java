/*
 * Lumeer: Modern Data Definition and Processing Platform
 *
 * Copyright (C) since 2017 Lumeer.io, s.r.o. and/or its affiliates.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.lumeer.api.util;

import io.lumeer.api.model.Attribute;

import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.Comparator;
import java.util.stream.Collectors;

public class AttributeUtil {

   public static boolean isEqualOrChild(Attribute attribute, String attributeName) {
      return attribute.getName().equals(attributeName) || attribute.getName().startsWith(attributeName + '.');
   }

   public static void renameChildAttributes(java.util.Collection<Attribute> attributes, String oldParentName, String newParentName) {
      String prefix = oldParentName + '.';
      attributes.forEach(attribute -> {
         if (attribute.getName().startsWith(prefix)) {
            renameChildAttribute(attribute, oldParentName, newParentName);
         }
      });
   }

   private static void renameChildAttribute(Attribute attribute, String oldParentName, String newParentName) {
      String[] parts = attribute.getName().split(oldParentName, 2);
      String newName = newParentName.concat(parts[1]);
      attribute.setName(newName);
   }

   public static java.util.Collection<Attribute> filterMostRelevantAttributes(java.util.Collection<Attribute> attributes, String text, int limit) {
      var distance = LevenshteinDistance.getDefaultInstance();
      return attributes.stream()
                       .filter(a -> a.getName().toLowerCase().contains(text))
                       .sorted(Comparator.comparingInt(a -> distance.apply(a.getName().toLowerCase(), text.toLowerCase())))
                       .limit(limit)
                       .collect(Collectors.toList());
   }

}
