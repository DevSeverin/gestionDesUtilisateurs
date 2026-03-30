// src/models/User.ts

export class User {
    name = "";
    email = "";
    provider = "";
    admin = false;
    accessToken = "";
    accessTokenExpirationAt = 0;
    refreshToken = "";

    static parse(accessToken: string, refreshToken: string): User {
        const res = new User();
        const accessTokenPayload = accessToken.split('.')[1];
        const parsedAccessTokenPayload = JSON.parse(atob(accessTokenPayload));
        res.name = parsedAccessTokenPayload.name;
        res.email = parsedAccessTokenPayload.sub;
        res.admin = parsedAccessTokenPayload.admin;
        res.accessTokenExpirationAt = parsedAccessTokenPayload.exp;
        res.accessToken = accessToken;
        res.refreshToken = refreshToken;
        return res;
    }

    static isLoggedIn(user: User): boolean {
        return user.name != "" && user.accessTokenExpirationAt * 1000 > Date.now();
    }

    static isNotLoggedIn(user: User): boolean {
        return !this.isLoggedIn(user);
    }

    static isAdmin(user: User): boolean {
        return User.isLoggedIn(user) && user.admin ? true : false;
    }

}

export const NOT_LOGGED_IN_USER = new User();