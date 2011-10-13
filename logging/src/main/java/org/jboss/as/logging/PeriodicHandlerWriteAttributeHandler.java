/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.as.logging;

import org.jboss.as.controller.OperationFailedException;
import org.jboss.dmr.ModelNode;
import org.jboss.logmanager.handlers.PeriodicRotatingFileHandler;

import static org.jboss.as.logging.CommonAttributes.APPEND;
import static org.jboss.as.logging.CommonAttributes.SUFFIX;

/**
 * Date: 13.10.2011
 *
 * @author <a href="mailto:jperkins@redhat.com">James R. Perkins</a>
 */
public class PeriodicHandlerWriteAttributeHandler extends LogHandlerWriteAttributeHandler<PeriodicRotatingFileHandler> {
    static final PeriodicHandlerWriteAttributeHandler INSTANCE = new PeriodicHandlerWriteAttributeHandler();

    private PeriodicHandlerWriteAttributeHandler() {
        super(APPEND, SUFFIX);
    }

    @Override
    protected boolean applyUpdateToRuntime(final ModelNode operation, final String attributeName, final ModelNode resolvedValue, final ModelNode currentValue, final PeriodicRotatingFileHandler handler) throws OperationFailedException {
        if (APPEND.getName().equals(attributeName)) {
            handler.setAppend(APPEND.validateResolvedOperation(operation).asBoolean());
        } else if (SUFFIX.getName().equals(attributeName)) {
            handler.setSuffix(SUFFIX.validateResolvedOperation(operation).asString());
        }
        return false;
    }

    @Override
    protected void revertUpdateToRuntime(final ModelNode operation, final String attributeName, final ModelNode valueToRestore, final ModelNode valueToRevert, final PeriodicRotatingFileHandler handler) throws OperationFailedException {
        if (APPEND.getName().equals(attributeName)) {
            handler.setAppend(APPEND.validateResolvedOperation(valueToRestore).asBoolean());
        } else if (SUFFIX.getName().equals(attributeName)) {
            handler.setSuffix(SUFFIX.validateResolvedOperation(valueToRestore).asString());
        }
    }
}
