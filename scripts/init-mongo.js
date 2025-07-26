// 명시적으로 onceaday DB 선택
db = db.getSiblingDB("onceaday");

// onceaday DB에 사용할 유저 생성
db.createUser({
    user: "appuser",
    pwd: "appUser1",
    roles: [
        {
            role: "readWrite",
            db: "onceaday"
        }
    ]
});
