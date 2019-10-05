// @flow

let num = 2;
console.log(num);
let chameleon = 2;
chameleon += '23';

console.log(chameleon);
const multiply = (a: number, b: number) => a*b;
console.log(multiply(3, 4));

// console.log(multiply('olA', 'kAri'));

const hello = (who: ?string) => {
    if (who) console.log(who);
    else console.log('Heloo wooorld');
};

hello();
hello('Trondaway');

class Person {
    name: string = '';
    age: number = 0;
    constructor(name: string, age: number) {
        this.name = name;
        this.age = age;
    }
}

let person = new Person('ola', 999);
console.table(person);
person.name = 'hei';
console.table(person);

// representerer en framtidig verdig/en feilverdi
let promise: Promise<number> = new Promise((resolve, reject) => {
    setTimeout(() => {
        console.log('...coming right up...');
        setTimeout(() => {
            if (Math.random() % 5 == 0) {
                resolve(42);
            } else {
                reject(666);
            }
        }, 1000);
    }, 1000);
});

console.log('answer is...');
promise.then(value => console.log(value)).catch(error => console.trace(error));
// WE GONNA DO async&await, YAY (but I already know it)
