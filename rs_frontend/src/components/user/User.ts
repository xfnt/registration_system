"use strict";

import {UserType} from "./UserType";

export class User {
    private _id: number;
    private _firstName: string;
    private _lastName: string;
    private _middleName: string;
    private _birthDate: Date;
    private _phoneNumber: string;
    private _userType: UserType;
    private _admin: boolean;
    private _deleted: boolean;

    constructor(id: number, firstName: string, lastName: string, middleName: string, birthDate: Date, phoneNumber: string, userType: UserType, admin: boolean, deleted: boolean) {
        this._id = id;
        this._firstName = firstName;
        this._lastName = lastName;
        this._middleName = middleName;
        this._birthDate = birthDate;
        this._phoneNumber = phoneNumber;
        this._userType = userType;
        this._admin = admin;
        this._deleted = deleted;
    }


    get id(): number {
        return this._id;
    }

    set id(value: number) {
        this._id = value;
    }

    get firstName(): string {
        return this._firstName;
    }

    set firstName(value: string) {
        this._firstName = value;
    }

    get lastName(): string {
        return this._lastName;
    }

    set lastName(value: string) {
        this._lastName = value;
    }

    get middleName(): string {
        return this._middleName;
    }

    set middleName(value: string) {
        this._middleName = value;
    }

    get birthDate(): Date {
        return this._birthDate;
    }

    set birthDate(value: Date) {
        this._birthDate = value;
    }

    get phoneNumber(): string {
        return this._phoneNumber;
    }

    set phoneNumber(value: string) {
        this._phoneNumber = value;
    }

    get userType(): UserType {
        return this._userType;
    }

    set userType(value: UserType) {
        this._userType = value;
    }

    get admin(): boolean {
        return this._admin;
    }

    set admin(value: boolean) {
        this._admin = value;
    }

    get deleted(): boolean {
        return this._deleted;
    }

    set deleted(value: boolean) {
        this._deleted = value;
    }
}