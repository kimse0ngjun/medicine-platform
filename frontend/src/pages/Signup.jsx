import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { signup } from "../api/auth";
import "../style/Signup.css";

export default function Signup() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    nickname: "",
    email: "",
    password: "",
    passwordConfirm: "",
  });

  const [message, setMessage] = useState({ type: "", text: "" });
  const [loading, setLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const [showPasswordConfirm, setShowPasswordConfirm] = useState(false);

  const getStrength = (pw) => {
    let score = 0;
    if (pw.length >= 8) score++;
    if (/[A-Z]/.test(pw)) score++;
    if (/[0-9]/.test(pw)) score++;
    if (/[^A-Za-z0-9]/.test(pw)) score++;
    return score;
  };

  const strengthLabel = ["", "약함", "보통", "강함", "매우 강함"];
  const strength = getStrength(form.password);

  /* ── 유효성 ── */
  const passwordMatch =
    form.password &&
    form.passwordConfirm &&
    form.password === form.passwordConfirm;
  const passwordMismatch =
    form.passwordConfirm && form.password !== form.passwordConfirm;

  const onChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const onSubmit = async (e) => {
    e.preventDefault();

    if (passwordMismatch) {
      setMessage({ type: "error", text: "비밀번호가 일치하지 않습니다." });
      return;
    }

    setLoading(true);
    setMessage({ type: "", text: "" });

    try {
      const res = await signup({
        nickname: form.nickname,
        email: form.email,
        password: form.password,
      });

      setMessage({
        type: "success",
        text: `${res.nickname}님, 가입이 완료되었습니다. 로그인 페이지로 이동합니다.`,
      });

      setTimeout(() => navigate("/auth/login"), 1800);
    } catch (err) {
      console.error("회원가입 실패:", err);
      setMessage({
        type: "error",
        text:
          err.response?.status === 409
            ? "이미 사용 중인 이메일입니다."
            : "회원가입 중 오류가 발생했습니다.",
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="signup-page">
      <div className="signup-card">
        <div className="signup-logo">
          <svg
            className="signup-logo__icon"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            strokeWidth="1.5"
          >
            <path d="M12 2L2 7l10 5 10-5-10-5zM2 17l10 5 10-5M2 12l10 5 10-5" />
          </svg>
          <span className="signup-logo__text">MediCheck</span>
        </div>

        <h1 className="signup-heading">회원가입</h1>
        <p className="signup-subheading">
          계정을 만들고 리콜 조회 기록을 저장하세요.
        </p>

        {message.text && (
          <div
            className={`alert alert--${message.type === "success" ? "success" : "error"}`}
            style={{ marginBottom: "20px" }}
          >
            {message.type === "success" ? (
              <svg
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                strokeWidth="2"
              >
                <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14" />
                <polyline points="22 4 12 14.01 9 11.01" />
              </svg>
            ) : (
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
            )}
            {message.text}
          </div>
        )}

        <form onSubmit={onSubmit} className="signup-form">
          <div className="form-group">
            <label className="form-label">닉네임</label>
            <input
              className="form-input"
              name="nickname"
              value={form.nickname}
              placeholder="홍길동"
              onChange={onChange}
              autoComplete="nickname"
              required
            />
          </div>

          <div className="form-group">
            <label className="form-label">이메일</label>
            <input
              className="form-input"
              name="email"
              type="email"
              value={form.email}
              placeholder="you@example.com"
              onChange={onChange}
              autoComplete="email"
              required
            />
          </div>

          <div className="form-group">
            <label className="form-label">비밀번호</label>
            <div className="form-input-group">
              <input
                className="form-input"
                name="password"
                type={showPassword ? "text" : "password"}
                value={form.password}
                placeholder="8자 이상 입력"
                onChange={onChange}
                autoComplete="new-password"
                required
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

            {form.password && (
              <div
                className={`password-strength password-strength--${strength}`}
              >
                <div className="password-strength__bars">
                  {[1, 2, 3, 4].map((n) => (
                    <div key={n} className="password-strength__bar" />
                  ))}
                </div>
                <span className="password-strength__label">
                  {strengthLabel[strength]}
                </span>
              </div>
            )}
          </div>

          <div className="form-group">
            <label className="form-label">비밀번호 확인</label>
            <div className="form-input-group">
              <input
                className={`form-input ${
                  passwordMatch
                    ? "form-input--valid"
                    : passwordMismatch
                      ? "form-input--invalid"
                      : ""
                }`}
                name="passwordConfirm"
                type={showPasswordConfirm ? "text" : "password"}
                value={form.passwordConfirm}
                placeholder="비밀번호를 한 번 더 입력"
                onChange={onChange}
                autoComplete="new-password"
                required
              />
              <button
                type="button"
                className="form-input-group__toggle"
                onClick={() => setShowPasswordConfirm(!showPasswordConfirm)}
                tabIndex={-1}
              >
                {showPasswordConfirm ? (
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

            {passwordMismatch && (
              <p className="form-error">
                <svg
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  strokeWidth="2"
                >
                  <circle cx="12" cy="12" r="10" />
                  <line x1="15" y1="9" x2="9" y2="15" />
                  <line x1="9" y1="9" x2="15" y2="15" />
                </svg>
                비밀번호가 일치하지 않습니다.
              </p>
            )}
          </div>

          <button
            className={`btn btn--primary btn--full${loading ? " btn--loading" : ""}`}
            type="submit"
            disabled={loading || passwordMismatch}
          >
            {loading && <span className="btn__spinner" />}
            {loading ? "처리 중..." : "회원가입"}
          </button>
        </form>

        <div className="signup-footer">
          이미 계정이 있으신가요?&nbsp;
          <a href="/auth/login">로그인</a>
        </div>
      </div>
    </div>
  );
}
