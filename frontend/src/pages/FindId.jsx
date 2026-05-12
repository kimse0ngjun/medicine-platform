import { useState } from "react";
import { findId } from "../api/auth";

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
        setResult(`아이디: ${res.email}`);
      } else {
        setResult(res.message);
      }
    } catch (err) {
      console.error(err);

      setResult("오류가 발생했습니다.");
    }
  };

  return (
    <div>
      <h1>아이디 찾기</h1>

      <form onSubmit={onSubmit}>
        <input name="nickname" placeholder="닉네임" onChange={onChange} />

        <input name="email" placeholder="이메일" onChange={onChange} />

        <button type="submit">아이디 찾기</button>
      </form>

      <p>{result}</p>
    </div>
  );
}
