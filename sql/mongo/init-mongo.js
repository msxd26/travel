db.createUser({
    user: 'root',
    pwd: 'toor',
    roles: [
        {
            role: 'readWrite',
            db: 'testDB',
        },
    ],
});
db.createCollection('app_users', {capped: false});

db.app_users.insert([
    {
        "username": "ragnar777",
        "dni": "VIKI771012HMCRG093",
        "enabled": true,
        "password": "$2a$10$3Gx2K.4bKbxE4PHQIRIG6.FIhz58qPJxVnhc8GqNw4YzaaTvjOO.2",
        "role":
            {
                "granted_authorities": ["ROLE_USER"]
            }
    },
    {
        "username": "heisenberg",
        "dni": "BBMB771012HMCRR022",
        "enabled": true,
        "password": "$2a$10$BjQJYO9TRu1AaYDcp8i.w.qfSpQuZt07jyTshypYFBDOSpaBcj98C",
        "role":
            {
                "granted_authorities": ["ROLE_USER"]
            }
    },
    {
        "username": "misterX",
        "dni": "GOTW771012HMRGR087",
        "enabled": true,
        "password": "$2a$10$crT6Ki43g61NchV1dLyri.JISYRbN8/n8b7CIlJsJXbo016NO1m06",
        "role":
            {
                "granted_authorities": ["ROLE_USER", "ROLE_ADMIN"]
            }
    },
    {
        "username": "neverMore",
        "dni": "WALA771012HCRGR054",
        "enabled": true,
        "password": "$2a$10$Z/7wscfb777DmjcKhsZ4Ke0TCiUUPbGv3CbiBRacEp7lqciEjOot2",
        "role":
            {
                "granted_authorities": ["ROLE_ADMIN"]
            }
    }
]);