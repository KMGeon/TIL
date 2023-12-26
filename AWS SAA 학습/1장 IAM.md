# IAM

---
## IAM

- 처음 생성할 때 처리하며 그 후에는 루트 계정을 더이상 사용, 공유해서는 안된다.
- 그 대신에  IAM을 사용한다.
- **그룹에는 오직 사용자만 포함한다.**
    - 한 사용자는 여러개의 그룹에 속할 수 있다.

```json
/* 하나의 정책에 다수의 Statements가 사용된 예 */
{
  "Version": "2012-10-17", // “2012–10–17”, “2008–10–17” 값을 가질 수 있다.
  "Statement": [
    {
      "Sid": "FirstStatement", // Statement ID로 statement 를 구분하기 위해서 사용
      "Effect": "Allow", // 해당 설정 적용을 Allow / Deny
			"Principal":{
					"AWS":["arm:aws:iam:123456789.root"]
			},
      "Action": [ // 허용할 행위(액션)
      	"s3:List*",
        "s3:Get*"
      ], 
      "Resource": [ // Action이 영향을 미치는 리소스 리스트를 지정
        "arn:aws:s3:::confidential-data",
        "arn:aws:s3:::confidential-data/*"
      ], 
      "Condition": {
      	"Bool": {"aws:MultiFactorAuthPresent": "true"}
      } // 조건을 충족되는 경우에만 해당 정책을 적용
    }
  ]
}
```

- sid : 문장 id로 문장의 식별자 → 선택사항
- Effect : 허용할지 안할지
- Principal : 특정 정책이 적용될 사용자, 계정, 혹은 역활로 구성된다.
- action, effect는 허용할 api 호출의 목록
- Resource : Action이 영향을 미치는 리소스 리스트를 지정
- statement가 언제 적용될지를 결정하는 condition
- effect, pricipal, action, resource는 중요하다.

## IAM

---

- 비밀번호 강화 정책 : 비밀번호 최소 길이, 특정 유형 글자를 요청 ( 물음표, 대문자 )
- 일정 기간 유지시키고 몇일 뒤에 변경하게 강재
- 비밀번호 변경 시 동일한 비밀번호 사용 불가능

1. 다 요소 인증 mfa
    1. 사용자들은 계정에 접근 권한이 있고 많은 작업을 할 수 있다.
    2. 비밀버호와 보안장치를 동시에 사용하여 보안을 처리한다.

→ 비밀번호 + mfa 토큰을 가지고 있어야 한다. ⇒ 로그인

- google authenticator : 휴대폰만
- authy  : 다양한 장치
- yubikey : 하나의 키로 iam 사용자에게 다 가능하다.

---

## Iam role

- aws에 어떤 ec2에 어떤 작업을 만들어 ec2에 접근하거나 호출을 막을 수 있습니다.