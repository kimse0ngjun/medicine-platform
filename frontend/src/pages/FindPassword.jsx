import { useState } from "react";

import { findPassword } from "../api/auth";

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
    <div>
      <h1>비밀번호 찾기</h1>

      <form onSubmit={onSubmit}>
        <input
          name="nickname"
          value={form.nickname}
          placeholder="닉네임"
          onChange={onChange}
        />

        <input
          name="email"
          value={form.email}
          placeholder="이메일"
          onChange={onChange}
        />

        <button type="submit">비밀번호 찾기</button>
      </form>

      <p>{result}</p>
    </div>
  );
}
