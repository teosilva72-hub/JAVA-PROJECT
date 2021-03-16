/* tslint:disable */
/* eslint-disable */
/**
*/
export enum PaginaType {
  Type1,
  Type2,
  Type3,
}
/**
*/
export class PMV {
  free(): void;
/**
* @param {number} id
* @param {string} type_alert
* @param {string} name
* @param {number} page_type
* @returns {PMV}
*/
  static new(id: number, type_alert: string, name: string, page_type: number): PMV;
/**
* @returns {number}
*/
  id(): number;
/**
* @param {string} value
* @returns {string}
*/
  type_alert(value: string): string;
/**
* @param {string} value
* @returns {string}
*/
  name(value: string): string;
/**
* @returns {number}
*/
  type_page(): number;
/**
* @param {number} idx
* @returns {Pagina}
*/
  page(idx: number): Pagina;
/**
* @returns {number}
*/
  len(): number;
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
  add_page(image_id: number, image_id_2: number, image: string, image_2: string, timer: number, line1: string, line2: string, line3: string): void;
/**
*/
  add_page_default(): void;
/**
* @param {number} idx
*/
  remove_page(idx: number): void;
/**
* @param {number} idx
* @param {string} line1
* @param {string} line2
* @param {string} line3
*/
  change_page(idx: number, line1: string, line2: string, line3: string): void;
/**
* @param {number} idx
* @param {number} image_id
* @param {number} image_id_2
* @param {string} image
* @param {string} image_2
*/
  change_image(idx: number, image_id: number, image_id_2: number, image: string, image_2: string): void;
/**
* @param {number} idx
* @param {number} timer
*/
  change_timer(idx: number, timer: number): void;
/**
* @returns {Pagina}
*/
  next(): Pagina;
/**
* @returns {PMV}
*/
  clone(): PMV;
}
/**
*/
export class Pagina {
  free(): void;
/**
* @returns {string}
*/
  string(): string;
/**
* @param {number} idx
* @returns {number}
*/
  image_id(idx: number): number;
/**
* @param {number} idx
* @returns {string}
*/
  image(idx: number): string;
/**
* @returns {number}
*/
  timer(): number;
/**
* @param {number} line
* @returns {string}
*/
  line(line: number): string;
/**
* @param {number} line
* @param {number} idx
* @returns {string}
*/
  line_char(line: number, idx: number): string;
}

export type InitInput = RequestInfo | URL | Response | BufferSource | WebAssembly.Module;

export interface InitOutput {
  readonly memory: WebAssembly.Memory;
  readonly __wbg_pagina_free: (a: number) => void;
  readonly pagina_string: (a: number, b: number) => void;
  readonly pagina_image_id: (a: number, b: number) => number;
  readonly pagina_image: (a: number, b: number, c: number) => void;
  readonly pagina_timer: (a: number) => number;
  readonly pagina_line: (a: number, b: number, c: number) => void;
  readonly pagina_line_char: (a: number, b: number, c: number) => number;
  readonly __wbg_pmv_free: (a: number) => void;
  readonly pmv_new: (a: number, b: number, c: number, d: number, e: number, f: number) => number;
  readonly pmv_id: (a: number) => number;
  readonly pmv_type_alert: (a: number, b: number, c: number, d: number) => void;
  readonly pmv_name: (a: number, b: number, c: number, d: number) => void;
  readonly pmv_type_page: (a: number) => number;
  readonly pmv_page: (a: number, b: number) => number;
  readonly pmv_len: (a: number) => number;
  readonly pmv_add_page: (a: number, b: number, c: number, d: number, e: number, f: number, g: number, h: number, i: number, j: number, k: number, l: number, m: number, n: number) => void;
  readonly pmv_add_page_default: (a: number) => void;
  readonly pmv_remove_page: (a: number, b: number) => void;
  readonly pmv_change_page: (a: number, b: number, c: number, d: number, e: number, f: number, g: number, h: number) => void;
  readonly pmv_change_image: (a: number, b: number, c: number, d: number, e: number, f: number, g: number, h: number) => void;
  readonly pmv_change_timer: (a: number, b: number, c: number) => void;
  readonly pmv_next: (a: number) => number;
  readonly pmv_clone: (a: number) => number;
  readonly __wbindgen_add_to_stack_pointer: (a: number) => number;
  readonly __wbindgen_free: (a: number, b: number) => void;
  readonly __wbindgen_malloc: (a: number) => number;
  readonly __wbindgen_realloc: (a: number, b: number, c: number) => number;
}

/**
* If `module_or_path` is {RequestInfo} or {URL}, makes a request and
* for everything else, calls `WebAssembly.instantiate` directly.
*
* @param {InitInput | Promise<InitInput>} module_or_path
*
* @returns {Promise<InitOutput>}
*/
export default function init (module_or_path?: InitInput | Promise<InitInput>): Promise<InitOutput>;
