/*
 * EvinceDaemon.cpp
 *
 * Copyright (C) 2009-12 by RStudio, Inc.
 *
 * This program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http://www.gnu.org/licenses/agpl-3.0.txt) for more details.
 *
 */

#include "EvinceDaemon.hpp"

namespace desktop {
namespace synctex {

EvinceDaemon::EvinceDaemon(QObject *parent)
    : QDBusAbstractInterface(QString::fromAscii("org.gnome.evince.Daemon"),
                             QString::fromAscii("/org/gnome/evince/Daemon"),
                             staticInterfaceName(),
                             QDBusConnection::sessionBus(),
                             parent)
{
}

EvinceDaemon::~EvinceDaemon()
{
}

} // namespace synctex
} // namespace desktop
