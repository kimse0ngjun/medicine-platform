import { useState } from "react";
import { login } from "../api/auth";

export default function Login() {
  const [form, setForm] = useState({
    email: "",
    password: "",
  });

  const onChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const onSubmit = async (e) => {
    e.preventDefault();

    try {
      const res = await login(form);
      console.log("로그인 성공:", res);
    } catch (err) {
      console.error("로그인 실패:", err);
    }
  };

  return (
    <div>
      <h1>Login</h1>

      <form onSubmit={onSubmit}>
        <input name="email" placeholder="email" onChange={onChange} />

        <input
          name="password"
          type="password"
          placeholder="password"
          onChange={onChange}
        />

        <button type="submit">로그인</button>
      </form>
    </div>
  );
}
