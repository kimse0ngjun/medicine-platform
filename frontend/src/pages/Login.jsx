import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../api/auth";
import "../style/Login.css";

export default function Login() {
  const navigate = useNavigate();

  const [form, setForm] = useState({ email: "", password: "" });
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

  const onChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const onSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage("");

    try {
      const res = await login(form);
      localStorage.setItem("nickname", res.nickname);
      navigate("/");
    } catch (err) {
      setMessage("아이디 또는 비밀번호가 올바르지 않습니다.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-page">
      <div className="login-card">
        <div className="login-logo" onClick={() => navigate("/")}>
          <svg
            className="login-logo__icon"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            strokeWidth="1.5"
          >
            <path d="M12 2L2 7l10 5 10-5-10-5zM2 17l10 5 10-5M2 12l10 5 10-5" />
          </svg>
          <span className="login-logo__text">MedicinePlatform</span>
        </div>

        <h1 className="login-heading">로그인</h1>
        <p className="login-subheading">계정에 로그인하여 계속하세요.</p>

        {message && (
          <div className="alert alert--error" style={{ marginBottom: "20px" }}>
            <svg
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeWidth="2"
            >
              <circle cx="12" cy="12" r="10" />
              <line x1="12" y1="8" x2="12" y2="12" />
              <line x1="12" y1="16" x2="12.01" y2="16" />
            </svg>
            {message}
          </div>
        )}

        <form onSubmit={onSubmit} className="login-form">
          <div className="form-group">
            <label className="form-label">이메일</label>
            <input
              className="form-input"
              name="email"
              type="email"
              placeholder="you@example.com"
              onChange={onChange}
              autoComplete="email"
            />
          </div>

          <div className="form-group">
            <label className="form-label">비밀번호</label>
            <div className="form-input-group">
              <input
                className="form-input"
                name="password"
                type={showPassword ? "text" : "password"}
                placeholder="••••••••"
                onChange={onChange}
                autoComplete="current-password"
              />
              <button
                type="button"
                className="form-input-group__toggle"
                onClick={() => setShowPassword(!showPassword)}
                tabIndex={-1}
              >
                {showPassword ? (
                  <svg
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="2"
                  >
                    <path d="M17.94 17.94A10.07 10.07 0 0112 20c-7 0-11-8-11-8a18.45 18.45 0 015.06-5.94" />
                    <path d="M9.9 4.24A9.12 9.12 0 0112 4c7 0 11 8 11 8a18.5 18.5 0 01-2.16 3.19" />
                    <line x1="1" y1="1" x2="23" y2="23" />
                  </svg>
                ) : (
                  <svg
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="2"
                  >
                    <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z" />
                    <circle cx="12" cy="12" r="3" />
                  </svg>
                )}
              </button>
            </div>
          </div>

          <div className="login-form__meta">
            <span />
            <div style={{ display: "flex", gap: "12px" }}>
              <a href="/find-id" className="form-forgot">
                아이디 찾기
              </a>
              <a href="/find-password" className="form-forgot">
                비밀번호 찾기
              </a>
            </div>
          </div>

          <button
            className={`btn btn--primary btn--full${loading ? " btn--loading" : ""}`}
            type="submit"
            disabled={loading}
          >
            {loading && <span className="btn__spinner" />}
            {loading ? "로그인 중..." : "로그인"}
          </button>
        </form>

        <div className="login-footer">
          계정이 없으신가요?&nbsp;
          <a href="/signup">회원가입</a>
        </div>
      </div>
    </div>
  );
}
