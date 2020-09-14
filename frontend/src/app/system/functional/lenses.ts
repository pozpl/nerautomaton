
export type Lens<T, U> = LensImpl<T, U>

export type Prism<T, U> = PrismImpl<T, U>
export type Prismish<T, U> = Lens<T, U> | Prism<T, U>;

export type Getter<T, U> = (target: T) => U
export type Setter<T> = (target: T) => T

export class LensImpl<T, U> {

    constructor(
        private _get: Getter<T, U>,
        private _set: (value: U) => Setter<T>
    ) { }

    public get(): Getter<T, U>;
    public get<V>(f: Getter<U, V>): Getter<T, V>;
    public get(){
        if (arguments.length){
            const f = arguments[0];
            return (t: T) => f(this._get(t))
        }else{
            return this._get;
        }
    }

    public set(value: U): Setter<T>;
    public set(f: Setter<U>): Setter<T>;
    public set(modifier: U | Setter<U>){
        if(typeof modifier === "function"){
            // @ts-ignore
            return  (t: T) => this._set(modifier(this._get(t)))(t);
        }else{
            return this._set(modifier);
        }
    }

    public compose<V>(other: Lens<U,V>): Lens<T,V> {
        return lens(
            t => other._get(this._get(t)),
            value => t => this._set( other._set(value)(this._get(t)) ) (t)
        )
    }

    public k<K extends keyof U>(key: K):Lens<T,U[K]>{
        return this.compose<U[K]>(lens(
                t => t[key],
            val => t => {
                const copied = copy(t);
                copied[key] = val;
                return copied;
                }
            )
        );
    }

    prop<K extends keyof T>(k: K): Lens<T, T[K]>;
    prop<K extends keyof T, K2 extends keyof T[K]>(k: K, k2: K2): Lens<T, T[K][K2]>;
    prop<K extends keyof T, K2 extends keyof T[K], K3 extends keyof T[K][K2]>(k: K, k2: K2, k3: K3): Lens<T, T[K][K2][K3]>;
    prop<K extends keyof T, K2 extends keyof T[K], K3 extends keyof T[K][K2], K4 extends keyof T[K][K2][K3]>(k: K, k2: K2, k3: K3, k4: K4): Lens<T, T[K][K2][K3][K4]>;
    prop(...keys: string[]): Lens<any, any> {
        let performUpdate: any;
        return lens(
            t => keys.reduce((x, k) => (x as any)[k], t), // [keys].reduce((prevVal, curVal) => reduceFn, firstVal) that's why (x as any)[k]
            v => t => {
                if (performUpdate === undefined) {
                    performUpdate = makeUpdater();
                }
                return performUpdate(t, v, keys, 0, keys.length - 1);
            }
        );
    }
}

function copy<T>(x: T): T {
    if (Array.isArray(x)) {
        return x.slice() as any;
    } else if (x && typeof x === 'object') {
        return Object.keys(x).reduce<any>((res, k) => {
            res[k] = (x as any)[k];
            return res;
        }, {});
    } else {
        return x;
    }
}

function makeUpdater() {
    const performUpdate = (
        o: any,
        v: any,
        keys: string[],
        idx: number,
        last: number
    ) => {
        const copy = { ...o };
        if (idx == last) {
            copy[keys[idx]] = v;
            return copy;
        } else {
            copy[keys[idx]] = performUpdate(o[keys[idx]], v, keys, idx + 1, last);
            return copy;
        }
    };
    return performUpdate;
}

function makeUpdaterOpt() {
    const performUpdate = (
        o: any,
        v: any,
        keys: string[],
        idx: number,
        last: number
    ) => {
        if (o === undefined){ // short circuit on undefined
            return o;
        }
        const copy = { ...o };
        if (idx == last) {
            copy[keys[idx]] = v;
            return copy;
        } else {
            copy[keys[idx]] = performUpdate(o[keys[idx]], v, keys, idx + 1, last);
            return copy;
        }
    };
    return performUpdate;
}

export function lens<T>(): Lens<T, T>;
export function lens<T, U>(_get: Getter<T, U>, _set: (value: U) => Setter<T>): Lens<T, U>;
export function lens() {
    if (arguments.length) {
        return new LensImpl(arguments[0], arguments[1]);//proxify(new LensImpl(arguments[0], arguments[1]));
    } else {
        return lens(t => t, v => _ => v);
    }
}


export class PrismImpl<T,U> {
    constructor(
        private _get: (T) => U | undefined,
        private _set: (U) => (T) => T
    ) { }

    public get(): (T) => U | undefined;
    public get<V>(f: (U) => V | undefined): (T) => V | undefined;
    public get(){
        if (arguments.length){
            const f = arguments[0];
            return (t: T) => f(this._get(t))
        }else{
            return this._get;
        }
    }

    public set(v: U): Setter<T> {
        return this._set(v);
    }


    public update(fn: (v: U | undefined) => U | undefined): (t: T) => T;
    public update(f: Function){
        const v = this.get();
        if (v === undefined) {
          return this;
        } else {
          return this.set(<U>f(v));
        }
    }


    public compose<V>(l1: Prismish<U, V>): Prism<T, V>;
    public compose<U1, V>(l1: Prismish<U, U1>, l2: Prismish<U1, V> ): Prism<T, V>;
    public compose<U1, U2, V>(l1: Prismish<T, U1>, l2: Prismish<U1, U2>, l3: Prismish<U2, V>): Prism<T, V>;
    public compose<U1, U2, U3, V>(l1: Prismish<T, U1>, l2: Prismish<U1, U2>, l3: Prismish<U2, U3>, l4: Prismish<U3, V> ): Prism<T, V>;
    public compose<U1, U2, U3, U4, V>( l1: Prismish<T, U1>, l2: Prismish<U1, U2>, l3: Prismish<U2, U3>, l4: Prismish<U3, U4>, l5: Prismish<U4, V> ): Prism<T, V>;
    public compose(...prisms: Prismish<any, any>[]): Prism<any, any> {
        let performComposedSet: any;
        return prism(
            (o: any) =>
                prisms.reduce((o, l) => (o === undefined ? o : l.get(o)), o),

            (v: any) => (o) => {
                if (performComposedSet === undefined) {
                    performComposedSet = this.makeComposedSetter();
                }
                return performComposedSet(o, v, prisms, 0);
            }
        );
    }


    prop<K extends keyof T>(k: K): Prism<T, T[K]>;
    prop<K extends keyof T, K2 extends keyof T[K]>(k: K, k2: K2): Prism<T, T[K][K2]>;
    prop<K extends keyof T, K2 extends keyof T[K], K3 extends keyof T[K][K2]>(k: K, k2: K2, k3: K3): Prism<T, T[K][K2][K3]>;
    prop<K extends keyof T, K2 extends keyof T[K], K3 extends keyof T[K][K2], K4 extends keyof T[K][K2][K3]>(k: K, k2: K2, k3: K3, k4: K4): Prism<T, T[K][K2][K3][K4]>;
    prop(...keys: string[]): Prism<any, any> {
        let performUpdate: any;
        return prism(
            t => keys.reduce((x, k) => x === undefined ? x : (x as any)[k], t), // [keys].reduce((prevVal, curVal) => reduceFn, firstVal) that's why (x as any)[k]
            v => t => {
                if (performUpdate === undefined) {
                    performUpdate = makeUpdaterOpt();
                }
                return performUpdate(t, v, keys, 0, keys.length - 1);
            }
        );
    }


    private makeComposedSetter() {
        const performComposedSet = (
            o: any,
            v: any,
            lenses: Lens<any, any>[],
            index: number
        ) => {
            if (index == lenses.length - 1) { //last lens set value to it
                return lenses[index].set( v)(o);
            } else {
                const inner = lenses[index].get()(o);
                if (inner) { //inner value defined assign value from the next level
                    return lenses[index].set(
                        performComposedSet(inner, v, lenses, index + 1)
                    )(o);
                } else { // undefined internal value just retun it without updating anything
                    return o;
                }
            }
        };
        return performComposedSet;
    }

}


export function prism<T>(): Prism<T, T>;
export function prism<T, U>(_get: Getter<T, U> , _set: (value: U) => Setter<T>): Prism<T, U>;
export function prism() {
    if (arguments.length) {
        return new PrismImpl(arguments[0], arguments[1]); //proxifyPrism(new PrismImpl(arguments[0], arguments[1]));
    } else {
        return prism(t => t, v => _ => v);
    }
}

