/**
 *  Copyright (C) 2008-2013 LimeTri. All rights reserved.
 *
 *  AgroSense is free software: you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software
 *  Foundation, either version 3 of the License, or (at your option) any later
 *  version.
 *
 *  There are special exceptions to the terms and conditions of the GPLv3 as it
 *  is applied to this software, see the FLOSS License Exception
 *  <http://www.agrosense.eu/foss-exception.html>.
 *
 *  AgroSense is distributed in the hope that it will be useful, but WITHOUT ANY
 *  WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 *  A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with
 *  AgroSense. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.gis.openweather.rendering.legends;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Frantisek Post
 */
public class TemperatureLegendPanel extends AbstractLegendPanel {

    public TemperatureLegendPanel() {
        super();
        initData();
    }

    private void initData() {
        Map<Float, Color> data = new LinkedHashMap<>();

        data.put(32f, new Color(173, 0, 0));
        data.put(20f, new Color(254, 75, 0));
        data.put(16f, new Color(254, 121, 0));
        data.put(12f, new Color(254, 183, 0));
        data.put(8f, new Color(254, 243, 0));
        data.put(4f, new Color(211, 254, 43));
        data.put(0f, new Color(155, 254, 99));
        data.put(-4f, new Color(103, 254, 151));
        data.put(-8f, new Color(43, 254, 211));
        data.put(-12f, new Color(0, 238, 254));
        data.put(-16f, new Color(0, 173, 254));
        data.put(-20f, new Color(0, 114, 254));
        data.put(-32f, new Color(0, 0, 180));

        setMap(data);
    }
}
