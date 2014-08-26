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

package org.apache.geronimo.mail.util;

import java.io.IOException;
import java.io.OutputStream;

public class HexEncoder
    implements Encoder
{
    protected final byte[] encodingTable =
        {
            (byte)'0', (byte)'1', (byte)'2', (byte)'3', (byte)'4', (byte)'5', (byte)'6', (byte)'7',
            (byte)'8', (byte)'9', (byte)'a', (byte)'b', (byte)'c', (byte)'d', (byte)'e', (byte)'f'
        };

    /*
     * set up the decoding table.
     */
    protected final byte[] decodingTable = new byte[128];

    protected void initialiseDecodingTable()
    {
        for (int i = 0; i < encodingTable.length; i++)
        {
            decodingTable[encodingTable[i]] = (byte)i;
        }

        decodingTable['A'] = decodingTable['a'];
        decodingTable['B'] = decodingTable['b'];
        decodingTable['C'] = decodingTable['c'];
        decodingTable['D'] = decodingTable['d'];
        decodingTable['E'] = decodingTable['e'];
        decodingTable['F'] = decodingTable['f'];
    }

    public HexEncoder()
    {
        initialiseDecodingTable();
    }

    /**
     * encode the input data producing a Hex output stream.
     *
     * @return the number of bytes produced.
     */
    public int encode(
        final byte[]                data,
        final int                    off,
        final int                    length,
        final OutputStream    out)
        throws IOException
    {
        for (int i = off; i < (off + length); i++)
        {
            final int    v = data[i] & 0xff;

            out.write(encodingTable[(v >>> 4)]);
            out.write(encodingTable[v & 0xf]);
        }

        return length * 2;
    }

    private boolean ignore(
        final char    c)
    {
        return (c == '\n' || c =='\r' || c == '\t' || c == ' ');
    }

    /**
     * decode the Hex encoded byte data writing it to the given output stream,
     * whitespace characters will be ignored.
     *
     * @return the number of bytes produced.
     */
    public int decode(
        final byte[]                data,
        final int                    off,
        final int                    length,
        final OutputStream    out)
        throws IOException
    {
        byte    b1, b2;
        int        outLen = 0;

        int        end = off + length;

        while (end > 0)
        {
            if (!ignore((char)data[end - 1]))
            {
                break;
            }

            end--;
        }

        int i = off;
        while (i < end)
        {
            while (i < end && ignore((char)data[i]))
            {
                i++;
            }

            b1 = decodingTable[data[i++]];

            while (i < end && ignore((char)data[i]))
            {
                i++;
            }

            b2 = decodingTable[data[i++]];

            out.write((b1 << 4) | b2);

            outLen++;
        }

        return outLen;
    }

    /**
     * decode the Hex encoded String data writing it to the given output stream,
     * whitespace characters will be ignored.
     *
     * @return the number of bytes produced.
     */
    public int decode(
        final String                data,
        final OutputStream    out)
        throws IOException
    {
        byte    b1, b2;
        int        length = 0;

        int        end = data.length();

        while (end > 0)
        {
            if (!ignore(data.charAt(end - 1)))
            {
                break;
            }

            end--;
        }

        int i = 0;
        while (i < end)
        {
            while (i < end && ignore(data.charAt(i)))
            {
                i++;
            }

            b1 = decodingTable[data.charAt(i++)];

            while (i < end && ignore(data.charAt(i)))
            {
                i++;
            }

            b2 = decodingTable[data.charAt(i++)];

            out.write((b1 << 4) | b2);

            length++;
        }

        return length;
    }
}
