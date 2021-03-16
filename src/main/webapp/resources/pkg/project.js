
let wasm;

let cachedTextDecoder = new TextDecoder('utf-8', { ignoreBOM: true, fatal: true });

cachedTextDecoder.decode();

let cachegetUint8Memory0 = null;
function getUint8Memory0() {
    if (cachegetUint8Memory0 === null || cachegetUint8Memory0.buffer !== wasm.memory.buffer) {
        cachegetUint8Memory0 = new Uint8Array(wasm.memory.buffer);
    }
    return cachegetUint8Memory0;
}

function getStringFromWasm0(ptr, len) {
    return cachedTextDecoder.decode(getUint8Memory0().subarray(ptr, ptr + len));
}

let cachegetInt32Memory0 = null;
function getInt32Memory0() {
    if (cachegetInt32Memory0 === null || cachegetInt32Memory0.buffer !== wasm.memory.buffer) {
        cachegetInt32Memory0 = new Int32Array(wasm.memory.buffer);
    }
    return cachegetInt32Memory0;
}

let WASM_VECTOR_LEN = 0;

let cachedTextEncoder = new TextEncoder('utf-8');

const encodeString = (typeof cachedTextEncoder.encodeInto === 'function'
    ? function (arg, view) {
    return cachedTextEncoder.encodeInto(arg, view);
}
    : function (arg, view) {
    const buf = cachedTextEncoder.encode(arg);
    view.set(buf);
    return {
        read: arg.length,
        written: buf.length
    };
});

function passStringToWasm0(arg, malloc, realloc) {

    if (realloc === undefined) {
        const buf = cachedTextEncoder.encode(arg);
        const ptr = malloc(buf.length);
        getUint8Memory0().subarray(ptr, ptr + buf.length).set(buf);
        WASM_VECTOR_LEN = buf.length;
        return ptr;
    }

    let len = arg.length;
    let ptr = malloc(len);

    const mem = getUint8Memory0();

    let offset = 0;

    for (; offset < len; offset++) {
        const code = arg.charCodeAt(offset);
        if (code > 0x7F) break;
        mem[ptr + offset] = code;
    }

    if (offset !== len) {
        if (offset !== 0) {
            arg = arg.slice(offset);
        }
        ptr = realloc(ptr, len, len = offset + arg.length * 3);
        const view = getUint8Memory0().subarray(ptr + offset, ptr + len);
        const ret = encodeString(arg, view);

        offset += ret.written;
    }

    WASM_VECTOR_LEN = offset;
    return ptr;
}
/**
*/
export const PaginaType = Object.freeze({ Type1:1,"1":"Type1",Type2:2,"2":"Type2",Type3:3,"3":"Type3", });
/**
*/
export class PMV {

    static __wrap(ptr) {
        const obj = Object.create(PMV.prototype);
        obj.ptr = ptr;

        return obj;
    }

    __destroy_into_raw() {
        const ptr = this.ptr;
        this.ptr = 0;

        return ptr;
    }

    free() {
        const ptr = this.__destroy_into_raw();
        wasm.__wbg_pmv_free(ptr);
    }
    /**
    * @param {number} id
    * @param {string} type_alert
    * @param {string} name
    * @param {number} page_type
    * @returns {PMV}
    */
    static new(id, type_alert, name, page_type) {
        var ptr0 = passStringToWasm0(type_alert, wasm.__wbindgen_malloc, wasm.__wbindgen_realloc);
        var len0 = WASM_VECTOR_LEN;
        var ptr1 = passStringToWasm0(name, wasm.__wbindgen_malloc, wasm.__wbindgen_realloc);
        var len1 = WASM_VECTOR_LEN;
        var ret = wasm.pmv_new(id, ptr0, len0, ptr1, len1, page_type);
        return PMV.__wrap(ret);
    }
    /**
    * @returns {number}
    */
    id() {
        var ret = wasm.pmv_id(this.ptr);
        return ret >>> 0;
    }
    /**
    * @param {string} value
    * @returns {string}
    */
    type_alert(value) {
        try {
            const retptr = wasm.__wbindgen_add_to_stack_pointer(-16);
            var ptr0 = passStringToWasm0(value, wasm.__wbindgen_malloc, wasm.__wbindgen_realloc);
            var len0 = WASM_VECTOR_LEN;
            wasm.pmv_type_alert(retptr, this.ptr, ptr0, len0);
            var r0 = getInt32Memory0()[retptr / 4 + 0];
            var r1 = getInt32Memory0()[retptr / 4 + 1];
            return getStringFromWasm0(r0, r1);
        } finally {
            wasm.__wbindgen_add_to_stack_pointer(16);
            wasm.__wbindgen_free(r0, r1);
        }
    }
    /**
    * @param {string} value
    * @returns {string}
    */
    name(value) {
        try {
            const retptr = wasm.__wbindgen_add_to_stack_pointer(-16);
            var ptr0 = passStringToWasm0(value, wasm.__wbindgen_malloc, wasm.__wbindgen_realloc);
            var len0 = WASM_VECTOR_LEN;
            wasm.pmv_name(retptr, this.ptr, ptr0, len0);
            var r0 = getInt32Memory0()[retptr / 4 + 0];
            var r1 = getInt32Memory0()[retptr / 4 + 1];
            return getStringFromWasm0(r0, r1);
        } finally {
            wasm.__wbindgen_add_to_stack_pointer(16);
            wasm.__wbindgen_free(r0, r1);
        }
    }
    /**
    * @returns {number}
    */
    type_page() {
        var ret = wasm.pmv_type_page(this.ptr);
        return ret;
    }
    /**
    * @param {number} idx
    * @returns {Pagina}
    */
    page(idx) {
        var ret = wasm.pmv_page(this.ptr, idx);
        return Pagina.__wrap(ret);
    }
    /**
    * @returns {number}
    */
    len() {
        var ret = wasm.pmv_len(this.ptr);
        return ret >>> 0;
    }
    /**
    * @param {number} image_id
    * @param {number} image_id_2
    * @param {string} image
    * @param {string} image_2
    * @param {number} timer
    * @param {string} line1
    * @param {string} line2
    * @param {string} line3
    */
    add_page(image_id, image_id_2, image, image_2, timer, line1, line2, line3) {
        var ptr0 = passStringToWasm0(image, wasm.__wbindgen_malloc, wasm.__wbindgen_realloc);
        var len0 = WASM_VECTOR_LEN;
        var ptr1 = passStringToWasm0(image_2, wasm.__wbindgen_malloc, wasm.__wbindgen_realloc);
        var len1 = WASM_VECTOR_LEN;
        var ptr2 = passStringToWasm0(line1, wasm.__wbindgen_malloc, wasm.__wbindgen_realloc);
        var len2 = WASM_VECTOR_LEN;
        var ptr3 = passStringToWasm0(line2, wasm.__wbindgen_malloc, wasm.__wbindgen_realloc);
        var len3 = WASM_VECTOR_LEN;
        var ptr4 = passStringToWasm0(line3, wasm.__wbindgen_malloc, wasm.__wbindgen_realloc);
        var len4 = WASM_VECTOR_LEN;
        wasm.pmv_add_page(this.ptr, image_id, image_id_2, ptr0, len0, ptr1, len1, timer, ptr2, len2, ptr3, len3, ptr4, len4);
    }
    /**
    */
    add_page_default() {
        wasm.pmv_add_page_default(this.ptr);
    }
    /**
    * @param {number} idx
    */
    remove_page(idx) {
        wasm.pmv_remove_page(this.ptr, idx);
    }
    /**
    * @param {number} idx
    * @param {string} line1
    * @param {string} line2
    * @param {string} line3
    */
    change_page(idx, line1, line2, line3) {
        var ptr0 = passStringToWasm0(line1, wasm.__wbindgen_malloc, wasm.__wbindgen_realloc);
        var len0 = WASM_VECTOR_LEN;
        var ptr1 = passStringToWasm0(line2, wasm.__wbindgen_malloc, wasm.__wbindgen_realloc);
        var len1 = WASM_VECTOR_LEN;
        var ptr2 = passStringToWasm0(line3, wasm.__wbindgen_malloc, wasm.__wbindgen_realloc);
        var len2 = WASM_VECTOR_LEN;
        wasm.pmv_change_page(this.ptr, idx, ptr0, len0, ptr1, len1, ptr2, len2);
    }
    /**
    * @param {number} idx
    * @param {number} image_id
    * @param {number} image_id_2
    * @param {string} image
    * @param {string} image_2
    */
    change_image(idx, image_id, image_id_2, image, image_2) {
        var ptr0 = passStringToWasm0(image, wasm.__wbindgen_malloc, wasm.__wbindgen_realloc);
        var len0 = WASM_VECTOR_LEN;
        var ptr1 = passStringToWasm0(image_2, wasm.__wbindgen_malloc, wasm.__wbindgen_realloc);
        var len1 = WASM_VECTOR_LEN;
        wasm.pmv_change_image(this.ptr, idx, image_id, image_id_2, ptr0, len0, ptr1, len1);
    }
    /**
    * @param {number} idx
    * @param {number} timer
    */
    change_timer(idx, timer) {
        wasm.pmv_change_timer(this.ptr, idx, timer);
    }
    /**
    * @returns {Pagina}
    */
    next() {
        var ret = wasm.pmv_next(this.ptr);
        return Pagina.__wrap(ret);
    }
    /**
    * @returns {PMV}
    */
    clone() {
        var ret = wasm.pmv_clone(this.ptr);
        return PMV.__wrap(ret);
    }
}
/**
*/
export class Pagina {

    static __wrap(ptr) {
        const obj = Object.create(Pagina.prototype);
        obj.ptr = ptr;

        return obj;
    }

    __destroy_into_raw() {
        const ptr = this.ptr;
        this.ptr = 0;

        return ptr;
    }

    free() {
        const ptr = this.__destroy_into_raw();
        wasm.__wbg_pagina_free(ptr);
    }
    /**
    * @returns {string}
    */
    string() {
        try {
            const retptr = wasm.__wbindgen_add_to_stack_pointer(-16);
            wasm.pagina_string(retptr, this.ptr);
            var r0 = getInt32Memory0()[retptr / 4 + 0];
            var r1 = getInt32Memory0()[retptr / 4 + 1];
            return getStringFromWasm0(r0, r1);
        } finally {
            wasm.__wbindgen_add_to_stack_pointer(16);
            wasm.__wbindgen_free(r0, r1);
        }
    }
    /**
    * @param {number} idx
    * @returns {number}
    */
    image_id(idx) {
        var ret = wasm.pagina_image_id(this.ptr, idx);
        return ret;
    }
    /**
    * @param {number} idx
    * @returns {string}
    */
    image(idx) {
        try {
            const retptr = wasm.__wbindgen_add_to_stack_pointer(-16);
            wasm.pagina_image(retptr, this.ptr, idx);
            var r0 = getInt32Memory0()[retptr / 4 + 0];
            var r1 = getInt32Memory0()[retptr / 4 + 1];
            return getStringFromWasm0(r0, r1);
        } finally {
            wasm.__wbindgen_add_to_stack_pointer(16);
            wasm.__wbindgen_free(r0, r1);
        }
    }
    /**
    * @returns {number}
    */
    timer() {
        var ret = wasm.pagina_timer(this.ptr);
        return ret;
    }
    /**
    * @param {number} line
    * @returns {string}
    */
    line(line) {
        try {
            const retptr = wasm.__wbindgen_add_to_stack_pointer(-16);
            wasm.pagina_line(retptr, this.ptr, line);
            var r0 = getInt32Memory0()[retptr / 4 + 0];
            var r1 = getInt32Memory0()[retptr / 4 + 1];
            return getStringFromWasm0(r0, r1);
        } finally {
            wasm.__wbindgen_add_to_stack_pointer(16);
            wasm.__wbindgen_free(r0, r1);
        }
    }
    /**
    * @param {number} line
    * @param {number} idx
    * @returns {string}
    */
    line_char(line, idx) {
        var ret = wasm.pagina_line_char(this.ptr, line, idx);
        return String.fromCodePoint(ret);
    }
}

async function load(module, imports) {
    if (typeof Response === 'function' && module instanceof Response) {

        if (typeof WebAssembly.instantiateStreaming === 'function') {
            try {
                return await WebAssembly.instantiateStreaming(module, imports);

            } catch (e) {
                if (module.headers.get('Content-Type') != 'application/wasm') {
                    console.warn("`WebAssembly.instantiateStreaming` failed because your server does not serve wasm with `application/wasm` MIME type. Falling back to `WebAssembly.instantiate` which is slower. Original error:\n", e);

                } else {
                    throw e;
                }
            }
        }

        const bytes = await module.arrayBuffer();
        return await WebAssembly.instantiate(bytes, imports);

    } else {

        const instance = await WebAssembly.instantiate(module, imports);

        if (instance instanceof WebAssembly.Instance) {
            return { instance, module };

        } else {
            return instance;
        }
    }
}

async function init(input) {
    if (typeof input === 'undefined') {
        input = new URL('project_bg.wasm', import.meta.url);
    }
    const imports = {};
    imports.wbg = {};
    imports.wbg.__wbg_log_6f54a0fb6df631a7 = function(arg0, arg1) {
        console.log(getStringFromWasm0(arg0, arg1));
    };
    imports.wbg.__wbindgen_throw = function(arg0, arg1) {
        throw new Error(getStringFromWasm0(arg0, arg1));
    };

    if (typeof input === 'string' || (typeof Request === 'function' && input instanceof Request) || (typeof URL === 'function' && input instanceof URL)) {
        input = fetch(input);
    }

    const { instance, module } = await load(await input, imports);

    wasm = instance.exports;
    init.__wbindgen_wasm_module = module;

    return wasm;
}

export default init;

