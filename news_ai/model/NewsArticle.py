from dataclasses import dataclass, field
from datetime import datetime
from typing import Optional

@dataclass
class NewsArticle:
    title: str
    content: str
    domain: str
    media: str
    href: str
    img_url: str
    created_date: datetime
    comics_url: Optional[str] = None
    summary: Optional[str] = None
    id: Optional[int] = field(default=None)