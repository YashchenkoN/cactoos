/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.func;

import java.util.HashMap;
import java.util.Map;
import org.cactoos.BiFunc;
import org.cactoos.scalar.StickyScalar;

/**
 * Func that accepts two arguments and caches previously calculated values
 * and doesn't recalculate again.
 *
 * <p>This {@link BiFunc} decorator technically is an in-memory
 * cache.</p>
 *
 * <p>There is no thread-safety guarantee.
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @param <X> Type of input
 * @param <Y> Type of input
 * @param <Z> Type of output
 * @see StickyScalar
 * @since 0.13
 */
public final class StickyBiFunc<X, Y, Z> implements BiFunc<X, Y, Z> {

    /**
     * Original func.
     */
    private final BiFunc<X, Y, Z> func;

    /**
     * Cache.
     */
    private final Map<Map<X, Y>, Z> cache;

    /**
     * Ctor.
     * @param fnc Func original
     */
    public StickyBiFunc(final BiFunc<X, Y, Z> fnc) {
        this.func = fnc;
        this.cache = new HashMap<>(0);
    }

    @Override
    public Z apply(final X first, final Y second) throws Exception {
        final Map<X, Y> keymap = new HashMap<>(1, 1.0F);
        keymap.put(first, second);
        if (!this.cache.containsKey(keymap)) {
            this.cache.put(keymap, this.func.apply(first, second));
        }
        return this.cache.get(keymap);
    }

}
