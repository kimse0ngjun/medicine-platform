import { useState } from "react";
import { Link } from "react-router-dom";
import { findPassword } from "../api/auth";
import "../style/FindPassword.css";

const IconLogo = () => (
  <svg
    className="findpw-logo__icon"
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="1.5"
  >
    <path d="M12 2L2 7l10 5 10-5-10-5zM2 17l10 5 10-5M2 12l10 5 10-5" />
  </svg>
);

export default function FindPassword() {
  const [form, setForm] = useState({
    nickname: "",
    email: "",
  });

  const [result, setResult] = useState("");

  const onChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const onSubmit = async (e) => {
    e.preventDefault();

    try {
      const res = await findPassword(form);
      setResult(res.message);
    } catch (err) {
      console.error(err);
      setResult("오류가 발생했습니다.");
    }
  };

  return (
    <div className="findpw-page">
      <div className="findpw-card">
        <Link to="/" className="findpw-logo">
          <IconLogo />
          <span className="findpw-logo__text">MedicinePlatform</span>
        </Link>

        <h1 className="findpw-heading">비밀번호 찾기</h1>

        <p className="findpw-subheading">
          가입 시 등록한 닉네임과 이메일을 입력하세요.
        </p>

        <form className="findpw-form" onSubmit={onSubmit}>
          <div className="form-group">
            <label className="form-label">닉네임</label>

            <input
              className="form-input"
              name="nickname"
              value={form.nickname}
              placeholder="닉네임"
              onChange={onChange}
            />
          </div>

          <div className="form-group">
            <label className="form-label">이메일</label>

            <input
              className="form-input"
              type="email"
              name="email"
              value={form.email}
              placeholder="example@email.com"
              onChange={onChange}
            />
          </div>

          <button className="btn btn--primary btn--full" type="submit">
            비밀번호 찾기
          </button>
        </form>

        {result && (
          <div className="email-sent-panel">
            <div className="email-sent-panel__title">{result}</div>
          </div>
        )}

        <div className="findpw-footer">
          <Link to="/auth/login">로그인</Link>

          <span className="findpw-footer__sep"></span>

          <Link to="/auth/find-id">아이디 찾기</Link>
        </div>
      </div>
    </div>
  );
}
