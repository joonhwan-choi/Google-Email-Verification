import React, { useState } from "react";
import "./App.css";

const App: React.FC = () => {
  const [email, setEmail] = useState<string>("");
  const [isEmailSent, setIsEmailSent] = useState<boolean>(false);
  const [userCode, setUserCode] = useState<string>(""); // 사용자가 입력한 인증 코드
  const [isVerified, setIsVerified] = useState<boolean>(false);

  // 이메일 입력 변화 처리
  const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };

  // 인증 코드 입력 변화 처리
  const handleCodeChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUserCode(e.target.value);
  };

  // 인증 코드 전송 함수
  const sendVerificationCode = async () => {
    try {
      // 로컬 서버에서 인증번호 전송 API 호출
      const response = await fetch("http://localhost:5000/api/v1/email/send", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ mail: email }),
      });

      if (!response.ok) {
        throw new Error("이메일 전송에 실패했습니다.");
      }

      const data = await response.text(); // 서버에서 받은 응답을 텍스트로 처리
      setIsEmailSent(true);  // 이메일이 성공적으로 전송됨
      alert(data);  // 서버로부터 받은 메시지 표시
    } catch (error) {
      console.error(error);
      alert("이메일 전송에 실패했습니다.");
    }
  };

  // 인증 코드 확인 함수
  const verifyCode = async () => {
    try {
      // 로컬 서버에서 인증번호 확인 API 호출
      const response = await fetch("http://localhost:5000/api/v1/email/verify", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          mail: email,
          verifyCode: userCode,
        }),
      });

      if (!response.ok) {
        throw new Error("인증번호 확인에 실패했습니다.");
      }

      const data = await response.text();  // 서버로부터 응답받은 메시지
      if (data === "인증이 완료되었습니다.") {
        setIsVerified(true);
        alert(data);  // 인증 성공 메시지
      } else {
        alert("잘못된 인증번호입니다. 다시 시도해주세요.");
      }
    } catch (error) {
      console.error(error);
      alert("인증번호 확인에 실패했습니다.");
    }
  };

  return (
    <div className="App">
      <h1>이메일 인증 테스트</h1>

      <div className="email-input">
        <input
          type="email"
          placeholder="이메일을 입력하세요"
          value={email}
          onChange={handleEmailChange}
        />
        <button onClick={sendVerificationCode}>인증번호 전송</button>
      </div>

      {isEmailSent && (
        <div className="code-input">
          <input
            type="text"
            placeholder="인증번호를 입력하세요"
            value={userCode}
            onChange={handleCodeChange}
          />
          <button onClick={verifyCode}>인증번호 확인</button>
        </div>
      )}

      {isVerified && <p>인증이 완료되었습니다!</p>}
    </div>
  );
};

export default App;
