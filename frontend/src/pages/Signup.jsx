import { useState } from "react";
import { signup } from "../api/auth";

export default function Signup() {
  const [form, setForm] = useState({
    nickname: "",
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
      const res = await signup(form);
      console.log("회원가입 성공:", res);
    } catch (err) {
      console.error("회원가입 실패:", err);
    }
  };

  return (
    <div>
      <h1>Signup</h1>

      <form onSubmit={onSubmit}>
        <input name="nickname" placeholder="nickname" onChange={onChange} />

        <input name="email" placeholder="email" onChange={onChange} />

        <input
          name="password"
          type="password"
          placeholder="password"
          onChange={onChange}
        />

        <button type="submit">회원가입</button>
      </form>
    </div>
  );
}
