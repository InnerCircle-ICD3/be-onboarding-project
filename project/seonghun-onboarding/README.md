# 설문 프로젝트

## 프로젝트 구조
> 여러 서버 인스턴스를 구현할 수 있으면 좋기 때문에 각 기능별로 층을 나누어 구현

- Repository : DB와 직접 통신하는 JPA 코드를 모아둔 폴더
- entity : entity class 들을 모아둔 폴더
- DTO : data 객체들을 모아둔 폴더
- Service : 핵심 도메인 기능이 구현된 폴더
- Controller : api가 구현된 폴더

## DB 스키마 구조

![DB구조](https://i.imgur.com/aNU5aT0.png)

## 전체 기능

- 설문 조사 생성
- 설문 조사 수정
- 설문 조사 조회
- 설문 조사 응답 제출
- 설문 조사 응답 조회

## API 명세서

### 설문조사 생성
<details>
<summary>설문조사 생성 api</summary>
<div markdown="1">

| Http Method    | Path            |
|---------|-----------------|
|  POST      | /surveys/create |

- Request

| Param       | Type       | Description |
|-------------|------------|------------|
| name        | String     | 설문이름       |
| description | String     | 설문 설명      |
| items       | List<item> | 질문 항목들 배열  |

> item 타입 형식

```

item : {
    name: String,
    description : String,
    type: ItemType,
    contents: List<String>
}
```

- Response

```
{
    "status": 200,
    "result": {
        "name": String,
        "description": String,
        "items": [
            {
                name: String,
                description : String,
                type: ItemType,
                contents: List<String>
            }
        ]
    }
}

```


</div>
</details>

### 설문조사 응답
<details>
<summary>설문조사 응답 제출 api</summary>
<div markdown="1">

| Http Method | Path            |
|------------|-----------------|
| POST       | /answers/submit |

- Request

| Param     | Type         | Description |
|-----------|--------------|------------|
| name      | String       | 응답자        |
| items     | json         | 질문 항목      |
| responses | List<String> | 응답 항목      |

> items json 형식
```
{
  "question" : String,
  "contents" : List<String>
}
```


- Response

```
{
    "status": 200,
    "result": 
        {
            "id": Long,
            "name": String,
            "items": {
                          "question" : String,
                          "contents" : List<String>
                        },
            "responses": List<String>
        }
}

```


</div>
</details>

## 상세 기능

### 설문 조사 생성

### 설문 조사 수정

### 설문 조사 조회(추가 기능)
> 실제 설문 응답을 제출하려면 기존의 설문 조사 항목들을 불러와야 되는 기능이 필요하다고 생각되어 해당 기능 추가

### 설문 조사 응답 제출

### 설문 조사 응답 조회

