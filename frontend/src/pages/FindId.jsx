import { useState } from "react";
import { Link } from "react-router-dom";
import { findId } from "../api/auth";
import "../style/FindId.css";

const IconLogo = () => (
  <svg
    className="findid-logo__icon"
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="1.5"
  >
    <path d="M12 2L2 7l10 5 10-5-10-5zM2 17l10 5 10-5M2 12l10 5 10-5" />
  </svg>
);

export default function FindId() {
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
      const res = await findId(form);

      if (res.success) {
        setResult(`아이디 : ${res.email}`);
      } else {
        setResult(res.message);
      }
    } catch (err) {
      console.error(err);
      setResult("오류가 발생했습니다.");
    }
  };

  return (
    <div className="findid-page">
      <div className="findid-card">
        <Link to="/" className="findid-logo">
          <IconLogo />
          <span className="findid-logo__text">MedicinePlatform</span>
        </Link>

        <h1 className="findid-heading">아이디 찾기</h1>

        <p className="findid-subheading">
          가입 시 등록한 닉네임과 이메일을 입력하세요.
        </p>

        <form className="findid-form" onSubmit={onSubmit}>
          <div className="form-group">
            <label className="form-label">닉네임</label>

            <input
              className="form-input"
              name="nickname"
              placeholder="닉네임"
              value={form.nickname}
              onChange={onChange}
            />
          </div>

          <div className="form-group">
            <label className="form-label">이메일</label>

            <input
              className="form-input"
              name="email"
              type="email"
              placeholder="example@email.com"
              value={form.email}
              onChange={onChange}
            />
          </div>

          <button className="btn btn--primary btn--full" type="submit">
            아이디 찾기
          </button>
        </form>

        {result && (
          <div className="findid-result" style={{ marginTop: "24px" }}>
            <div className="findid-result__label">RESULT</div>

            <div className="findid-result__email">{result}</div>
          </div>
        )}

        <div className="findid-footer">
          <Link to="/auth/login">로그인</Link>

          <span className="findid-footer__sep"></span>

          <Link to="/auth/find-password">비밀번호 찾기</Link>
        </div>
      </div>
    </div>
  );
}
